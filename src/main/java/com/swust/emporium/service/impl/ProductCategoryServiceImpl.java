package com.swust.emporium.service.impl;

import com.swust.emporium.dao.ProductCategoryDao;
import com.swust.emporium.dao.ProductDao;
import com.swust.emporium.dto.ProductCategoryExecution;
import com.swust.emporium.enums.ProductCategoryStateEnum;
import com.swust.emporium.exceptions.ProductCategoryOpertionException;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.Product;
import com.swust.emporium.pojo.ProductCategory;
import com.swust.emporium.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别业务逻辑实体类
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Autowired
    private ProductDao productDao;

    @Override
    public ProductCategoryExecution queryGetProductCategoryList(long shopId) {
        if (shopId <= 0){
            throw new ProductOperationException("error ==>" + "商品类别Id号错误");
        }

        ProductCategoryExecution pce = null;
        List<ProductCategory> productCategorys = null;
        try {
           productCategorys = productCategoryDao.queryProductCategory(shopId);
           pce = new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS,productCategorys);
        }catch (Exception e){
            pce = new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR, productCategorys);
        }
        return pce;
    }

    /***
     * 删除一条信息
     * @param productCategory
     * @return
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(ProductCategory productCategory) throws ProductOperationException{

        if (productCategory == null || productCategory.getShopId() == null || productCategory.getProductCategoryId() == null){
            return new ProductCategoryExecution(ProductCategoryStateEnum.NULL_SHOPID,productCategory);
        }
        // TODO... 将此商品类别下的商品类别置为空
        Long productCategoryId = productCategory.getProductCategoryId();
        int effectNums = productDao.updateProductCategoryToNull(productCategoryId);
        if (effectNums < 0){
            throw new ProductCategoryOpertionException("err ==> " + "商品类别置空失败");
        }

        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategory);
            if (effectNum < 0){
                throw new ProductOperationException("err ==> " + "删除商品操作失败");
            }
        }catch (Exception e){
            return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR, productCategory);
        }
        return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategory);

    }

    @Override
    @Transactional
    public ProductCategoryExecution batchInsertProductCategoryList(List<ProductCategory> productCategoryList) {
        ProductCategoryExecution pce = null;
        if (productCategoryList.size() <= 0 || productCategoryList == null){
            pce = new ProductCategoryExecution(ProductCategoryStateEnum.NULL_SHOPID, productCategoryList);
            return pce;
        }
        try {
            int insertNums = productCategoryList.size();
            int effectNums = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if (effectNums != insertNums){
                throw new ProductOperationException("error ==> " + "批量插入数据失败");
            }else {
                pce = new ProductCategoryExecution(
                        ProductCategoryStateEnum.SUCCESS, productCategoryList);
            }
        }catch (Exception e){
            pce = new ProductCategoryExecution(
                    ProductCategoryStateEnum.INNER_ERROR, productCategoryList);
        }
        return pce;
    }
}
