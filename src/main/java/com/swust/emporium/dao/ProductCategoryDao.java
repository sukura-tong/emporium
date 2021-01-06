package com.swust.emporium.dao;

import com.swust.emporium.pojo.ProductCategory;
import com.swust.emporium.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别操作数据库Dao层
 */
public interface ProductCategoryDao {
    /***
     * 查询产品类别
     * @return
     */
    List<ProductCategory> queryProductCategory(long shopId);

    /**
     * 根据类别Id从数据库删除信息
     * @param productCategoryId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryCondition") ProductCategory productCategory);

    /***
     * 商品类别的批量添加
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
