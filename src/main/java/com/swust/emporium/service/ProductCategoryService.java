package com.swust.emporium.service;

import com.beust.jcommander.ParameterException;
import com.swust.emporium.dto.ProductCategoryExecution;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.ProductCategory;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 产品类别操作业务逻辑层
 */
public interface ProductCategoryService {
    /**
     * 根据权重查询出产品数据
     * @return
     */
    ProductCategoryExecution queryGetProductCategoryList(long shopId);

    /**
     * 删除信息
     * 将此类别下的商品里的类别Id置为空，再删除掉该商品的类别
     * @param productCategory
     * @return
     */
    ProductCategoryExecution deleteProductCategory(ProductCategory productCategory);

    /***
     * 批量添加数据
     */

    ProductCategoryExecution batchInsertProductCategoryList(List<ProductCategory> productCategoryList)
            throws ProductOperationException;
}
