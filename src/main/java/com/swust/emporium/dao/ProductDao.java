package com.swust.emporium.dao;

import com.swust.emporium.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品信息管理数据库Dao层
 */
public interface ProductDao {
    /**
     * 添加商品信息
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /***
     * 根据传入的productID实现数据查询
     * @param productId
     * @return
     */
    Product queryProductById(Long productId);
    /***
     * 根据传入的商品实体类修改商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /***
     * 查询商品列表并分页展示
     *      商品名称模糊查询 店铺Id 商品类别
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(
            @Param("productCondition")Product productCondition,
            @Param("rowIndex") int rowIndex,
            @Param("pageSize") int pageSize);

    /**
     * 分页条件下的商品总数
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    int queryProductListCount(@Param("productCondition")Product productCondition);

    /**
     * 删除商品类别前 将商品类别 ID置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

}
