package com.swust.emporium.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ProductCategoryExecution;
import com.swust.emporium.dto.ProductExecution;
import com.swust.emporium.enums.ProductStateEnum;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.Product;
import com.swust.emporium.pojo.ProductCategory;
import com.swust.emporium.pojo.Shop;
import com.swust.emporium.service.ProductCategoryService;
import com.swust.emporium.service.ProductService;
import com.swust.emporium.util.CodeUtils;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品管理controller
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    // 支持上传文件的最大数目
    private static final int IMAGE_MAX_COUNT = 6;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 校验码
        if (CodeUtils.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的校验码");
            return modelMap;
        }

        // 接收前端参数
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();

        Product product = null;

        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }else {
            Shop shop = new Shop();
            shop.setShopId(currentShop.getShopId());
//            shop.setShopId(1L);
            product.setShop(shop);
        }

        // 判断是否有文件上传流
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        CommonsMultipartFile commonsMultipartFile = null;
        ImageHolder thumbnail = null;// 缩略图
        InputStream inputImgStream = null;

        List<ImageHolder> productImgList = new ArrayList<>();//详情图片
        CommonsMultipartFile commonsMultipartProductFile = null;
        InputStream inputProductImgStream = null;

        try {
           if (commonsMultipartResolver.isMultipart(request)){
               MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
               MultipartFile productImg = multipartHttpServletRequest.getFile("thumbnail");
               commonsMultipartFile = (CommonsMultipartFile)productImg;

               // 处理图片流
               thumbnail = new ImageHolder();
               String filename = commonsMultipartFile.getOriginalFilename();
               inputImgStream = commonsMultipartFile.getInputStream();
               thumbnail.setImage(inputImgStream);
               thumbnail.setImageName(filename);

               // 处理详情图片 最多支持六张图片
               for (int i = 0; i < IMAGE_MAX_COUNT; i++){
                   MultipartFile productImgFile = multipartHttpServletRequest.getFile("productImg" + i);
                   commonsMultipartProductFile = (CommonsMultipartFile)productImgFile;
                   // 若取出的文件不为空 则追加到文件列表中
                   if (commonsMultipartProductFile != null){
                       ImageHolder productFileImg =  new ImageHolder();
                       String originalFilename = commonsMultipartProductFile.getOriginalFilename();
                       inputProductImgStream = commonsMultipartProductFile.getInputStream();
                       productFileImg.setImageName(originalFilename);
                       productFileImg.setImage(inputProductImgStream);
                       productImgList.add(productFileImg);
                   }else {
                       // 图片为空 跳出循环
                       break;
                   }
               }

           }else {
               modelMap.put("success", false);
               modelMap.put("errMsg", "请输入图片信息");
               return modelMap;
           }
       }catch (ProductOperationException | IOException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
       }



        ProductExecution productExecution = null;
        if (product != null && thumbnail != null && productImgList != null){
            productExecution = productService.addProduct(product, thumbnail, productImgList);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入相关信息");
            return modelMap;
        }
        // 判断插入情况

        if (productExecution != null){
            if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()){
                modelMap.put("success", true);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productExecution.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "产品信息插入失败");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam Long productId ){
        Map<String, Object> modelMap = new HashMap<>();

        if (productId < 0){
            modelMap.put("success", false);
            modelMap.put("errMsg", "产品Id为空");
            return modelMap;
        }
        // 进行查询
        ProductExecution pe = null;
        try {
            pe = productService.queryProductById(productId);
        }catch (ProductOperationException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        // 结果判断
        if (pe != null){
            if (pe.getState()  == 1){
                modelMap.put("success", true);
                Product product = pe.getProduct();
                // 获取该店铺下的商品类别列表
                ProductCategoryExecution pce = productCategoryService.queryGetProductCategoryList(product.getShop().getShopId());
                List<ProductCategory> productCategoryList = pce.getProductCategoryList();

                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("product",product);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "店铺信息查询失败");
            return modelMap;
        }

    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(HttpServletRequest request){
        System.out.println("guolaill");
        Map<String, Object> modelMap = new HashMap<>();

        // 是否传入验证码
        Boolean staturChange = HttpServletRequestUtils.getBoolean(request, "staturChange");

        if (staturChange == false){
            if (!CodeUtils.checkVerifyCode(request)){
                modelMap.put("success", false);
                modelMap.put("errMsg", "请输入正确验证码");
                return modelMap;
            }
        }

        Shop currentShop = (Shop) request.getSession()
                .getAttribute("currentShop");

        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();

        Product product = null;
        try {
             product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // 获取文件上传流
        // 判断是否有文件上传流
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());

        CommonsMultipartFile commonsMultipartFile = null;
        ImageHolder thumbnail = null;// 缩略图
        InputStream inputImgStream = null;

        List<ImageHolder> productImgList = new ArrayList<>();//详情图片
        CommonsMultipartFile commonsMultipartProductFile = null;
        InputStream inputProductImgStream = null;

        try {
            if (cmr.isMultipart(request)){
                // 如果有图片流则进行图片数据接收处理 否则跳过

                MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest)request;
                MultipartFile productImg = mhsr.getFile("thumbnail");
                commonsMultipartFile = (CommonsMultipartFile)productImg;

                // 处理图片流
                thumbnail = new ImageHolder();
                String filename = commonsMultipartFile.getOriginalFilename();
                inputImgStream = commonsMultipartFile.getInputStream();
                thumbnail.setImage(inputImgStream);
                thumbnail.setImageName(filename);

                // 处理详情图片 最多支持六张图片
                for (int i = 0; i < IMAGE_MAX_COUNT; i++){
                    MultipartFile productImgFile = mhsr.getFile("productImg" + i);
                    commonsMultipartProductFile = (CommonsMultipartFile)productImgFile;
                    // 若取出的文件不为空 则追加到文件列表中
                    if (commonsMultipartProductFile != null){
                        ImageHolder productFileImg =  new ImageHolder();
                        String originalFilename = commonsMultipartProductFile.getOriginalFilename();
                        inputProductImgStream = commonsMultipartProductFile.getInputStream();
                        productFileImg.setImageName(originalFilename);
                        productFileImg.setImage(inputProductImgStream);
                        productImgList.add(productFileImg);
                    }else {
                        // 图片为空 跳出循环
                        break;
                    }
                }
            }
        }catch (ProductOperationException | IOException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // 进行更新
        ProductExecution pe = null;
        if (product != null){
            try {
                product.setShop(currentShop);
                pe = productService.updateProduct(product, thumbnail, productImgList);
            }catch (ProductOperationException exception){
                modelMap.put("success", false);
                modelMap.put("errMsg", exception.getMessage());
                return modelMap;
            }
        }

        if (pe != null){
            int state = pe.getState();
            if (state == 1){
                modelMap.put("success", true);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品信息更新失败");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        int pageIndex;
        int pageSize;
        // 接收前台传递的页码数据
        pageIndex = HttpServletRequestUtils.getInt(request,"pageIndex");
        pageSize = HttpServletRequestUtils.getInt(request, "pageSize");

        // 空值判断
        if (!(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本信息缺失");
            return modelMap;
        }

        // 获取查询条件
        long productCategoryId = HttpServletRequestUtils.getLong(request, "productCategoryId");
        String productName = HttpServletRequestUtils.getString(request, "productName");
        Long shopId = currentShop.getShopId();

        Product productCondition = null;
        try {
            productCondition = compactProductCondition(shopId, productCategoryId, productName);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (productCondition == null ){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入信息为空");
            return modelMap;
        }

        productCondition.setShop(currentShop);


        ProductExecution pe = null;
        try {
             pe = productService.queryProductList(productCondition, pageIndex, pageSize);
        }catch (ProductOperationException exception){
            modelMap.put("success", false);
            modelMap.put("errMsg", exception.getMessage());
            return modelMap;
        }

        if (pe != null){

            if (pe.getState() == 1){
                modelMap.put("success", true);
                modelMap.put("count", pe.getCount());
                modelMap.put("productList",pe.getProductList());
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "信息查询失败");
            return modelMap;
        }
    }

    /***
     * 获取查询相关条件
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();

        Shop shop = new Shop();
        shop.setShopId(shopId);

        productCondition.setShop(shop);

        if (productCategoryId != -1L){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if (productName != null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

    @RequestMapping(value = "/changeproductstatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeProductStatus(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
             product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        ProductExecution pe = null;
        product.setShop(currentShop);
        try {
            pe = productService.updateProduct(product, null, null);
        }catch (ProductOperationException exception){
            modelMap.put("success", false);
            modelMap.put("errMsg", exception.getMessage());
            return modelMap;
        }

        if (pe != null){
            int state = pe.getState();
            if (state == 1){
                modelMap.put("success", true);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品状态更新失败");
            return modelMap;
        }
    }
}
