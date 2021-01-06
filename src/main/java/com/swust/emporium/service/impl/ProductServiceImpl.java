package com.swust.emporium.service.impl;

import com.swust.emporium.dao.ProductDao;
import com.swust.emporium.dao.ProductImgDao;
import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ProductExecution;
import com.swust.emporium.enums.ProductStateEnum;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.Product;
import com.swust.emporium.pojo.ProductImg;
import com.swust.emporium.service.ProductService;
import com.swust.emporium.util.ImageUtils;
import com.swust.emporium.util.PageCalculatorUtil;
import com.swust.emporium.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品操作业务逻辑层
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    /***
     * 方法作用
     *  处理缩略图、获取相对路径并赋值给product
     *  向tb_product写入商品信息
     *  结合productId批量处理商品详情图
     *  将商品详情图列表批量插入到tb_product_img中
     * @param product
     * @param thumbnail
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolders) throws ProductOperationException {

        ProductExecution pe = null;

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null){
            // 设置初始状态 默认上架
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);

            if (thumbnail != null){
                addImgThumbnail(product, thumbnail);
            }
            // 向产品表内写入数据
            try {
                int effectNums = productDao.insertProduct(product);
                if (effectNums <= 0){
                    throw new ProductOperationException("err ==> 产品信息插入失败");
                }
            }catch (ProductOperationException exception){
                pe = new ProductExecution(ProductStateEnum.INNER_ERROR, product);
                return pe;
            }

            if (productImgHolders != null && productImgHolders.size() > 0){
                addProductImgThumbnailLists(product, productImgHolders);
            }

            pe = new ProductExecution(ProductStateEnum.SUCCESS, product);
            return pe;

        }else {
            pe = new ProductExecution(ProductStateEnum.NULL_PRODUCT, product);
            return pe;
        }

    }

    /**
     * 添加缩略图
     * @param product
     * @param imageHolder
     */
    public void addImgThumbnail(Product product, ImageHolder imageHolder){
        String imageName = imageHolder.getImageName();
        Long shopId = product.getShop().getShopId();
        // 获取shop的图片目录路径
        String destPath = PathUtils.getProductDescribeImgPath(shopId);
        // 添加水印 并返回图像存储路径
        String shopImgAddr = ImageUtils.generateThumbnail(imageHolder, destPath);
        product.setImgAddr(shopImgAddr);
    }

    /***
     * 批量添加图片
     * @param product
     * @param imageHolders
     */
    public void addProductImgThumbnailLists(Product product, List<ImageHolder> imageHolders){
        Long shopId = product.getShop().getShopId();
        String basePath = PathUtils.getProductImgPath(shopId); //  F:/project/image

        List<ProductImg> productImgList = new ArrayList<>();
        // 给图片添加水印
        for (ImageHolder imageHolder : imageHolders){
            String shopImgAddr = ImageUtils.generateNormalImg(imageHolder, basePath);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImg.setImgAddr(shopImgAddr);
            productImgList.add(productImg);
        }

        if (productImgList != null && productImgList.size() > 0){
            try {

                int effectNums = productImgDao.batchInsertProductImg(productImgList);
                if (effectNums != productImgList.size()){
                    throw new ProductOperationException("err ==> 详情图片信息插入失败");
                }

            }catch (ProductOperationException exception){
                throw new ProductOperationException("err ==> 详情图片信息插入失败 message ==>" + exception.getMessage());
            }
        }
    }

    /***
     * 根据Id查询相关信息
     * @param productId
     * @return
     */
    @Override
    public ProductExecution queryProductById(long productId) {
        ProductExecution pe = null;
        if (productId < 0){
            pe = new ProductExecution(ProductStateEnum.NULL_PRODUCT_ID);
        }
        Product product = null;
        try {
           product = productDao.queryProductById(productId);
        }catch (ProductOperationException exception){
            throw new ProductOperationException("err ==> " + exception.getMessage());
        }
        // 处理图片路径

        String imgAddr = product.getImgAddr();
        if (imgAddr != null){
            String replaceMaoHao = PathUtils.replaceMaoHao(imgAddr);
            product.setImgAddr(replaceMaoHao);
        }


        if (product != null){
            pe = new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else {
            pe = new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
        return pe;
    }

    /**
     * 更新数据库内的商品信息
     * 该方法需要实现两部分 第一部分 如果有图像信息 则先处理图像信息
     *          1 删除路径下相关的图片信息
     *          2 重新处理图像路径信息
     * 在将数据追加到数据库内
     * @param product
     * @return
     */
    @Override
    @Transactional
    public ProductExecution updateProduct(Product product, ImageHolder thumbnail , List<ImageHolder> imageHolders) {
        ProductExecution pe = null;
        if(!(product != null && product.getShop() != null && product.getShop().getShopId() != null)){
            pe = new ProductExecution(ProductStateEnum.NULL_PRODUCT, product);
            return pe;
        }

        // 是否具备文件流
        // 处理图片信息
        Long productId = product.getProductId();

        if (thumbnail != null){
            // 删除路径下的图片信息
                //获取路径
            String smallPicturePath = getSmallPicturePath(productDao, productId);

            if (smallPicturePath != null && !"".equals(smallPicturePath)){
                List<String> input = new ArrayList<>();
                input.add(smallPicturePath);
                int result = PathUtils.deleteInpuPath(input);
                if (result != 1){
                    throw new ProductOperationException("err ==> " + "缩略图路径删除失败");
                }
            }
            // 处理缩略图
            addImgThumbnail(product, thumbnail);
        }

        try {
            int effectNums = productDao.updateProduct(product);
            if (effectNums < 0){
                throw new ProductOperationException("err ==> " + "产品基本信息更新失败");
            }
        }catch (ProductOperationException exception){
            throw new ProductOperationException("err ==> " + exception.getMessage());
        }
        // 更新详情图片信息
        if (imageHolders != null && imageHolders.size() > 0){
            // 获取图片路径 并清空表
            List<String> picturePaths = getTruePicturePathAndDeleteImgTable(productImgDao, productId);
            // 物理删除
            int result = PathUtils.deleteInpuPath(picturePaths);
            if (result != 1){
                throw new ProductOperationException("err ==> " + "详情图路径删除失败");
            }

            try {
                addProductImgThumbnailLists(product, imageHolders);
            }catch (ProductOperationException exception){
                throw new ProductOperationException("err ==>" + exception.getMessage());
            }
        }

        pe = new ProductExecution(ProductStateEnum.SUCCESS, product);
        return pe;
    }

    /**
     * 分页查询相关内容
     * @param product
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution queryProductList(Product product, int pageIndex, int pageSize) {
        ProductExecution pe = null;

        if (product  == null){
            pe = new ProductExecution(ProductStateEnum.NULL_PRODUCT, product);
        }

        // 将pageIndex 和 rowIndex 相互转换
        int rowIndex = PageCalculatorUtil.calculateRowIndex(pageIndex, pageSize);

        List<Product> products = null;
        int counts = 0;

        try {
            products = productDao.queryProductList(product, rowIndex, pageSize);
            counts = productDao.queryProductListCount(product);
            if (products.size() < 0 || products.size() > counts){
                pe = new ProductExecution(ProductStateEnum.INNER_ERROR, product);
            }
        }catch (ProductOperationException exception){
            throw new ProductOperationException("err ==> " + exception.getMessage());
        }

        // 处理图像
        for (Product elemProduct : products){
            String imgAddr = elemProduct.getImgAddr();
            String replaceMaoHao = PathUtils.replaceMaoHao(imgAddr);
            elemProduct.setImgAddr(replaceMaoHao);
        }

        pe = new ProductExecution(ProductStateEnum.SUCCESS, products);
        pe.setCount(counts);

        return pe;
    }

    /***
     * 获取产品缩略图信息
     * @param productDao
     * @param productId
     * @return
     */
    public static String getSmallPicturePath(ProductDao productDao, Long productId){
        Product product = productDao.queryProductById(productId);

        if (product != null){
            String imgAddr = product.getImgAddr();
            if (imgAddr != null){
                return imgAddr;
            }else {
                return null;
            }
        }else {
            throw new ProductOperationException("err ==> " + "产品信息不存在");
        }
    }

    /***
     * 获取产品详情图片路径同时删除数据库和该商品Id相关的图片信息
     * @param productImgDao
     * @param productId
     * @return
     */
    public static List<String> getTruePicturePathAndDeleteImgTable(ProductImgDao productImgDao, Long productId){

        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        List<String> paths = new ArrayList<>();

        if (productImgList != null){
            for (ProductImg productImg : productImgList){
                if (productImg != null){
                    String path = productImg.getImgAddr();
                    paths.add(path);
                }
            }
        }else {
            return null;
        }

        // 删除该商品的所有图片信息
        try {
            int effectNums = productImgDao.deleteProductImgByProductId(productId);
            if (effectNums != paths.size()){
                throw new ProductOperationException("商品详情图片删除失败");
            }
        }catch (ProductOperationException exception){
            throw new ProductOperationException("err ==> " + exception.getMessage());
        }

        return paths;
    }
}
