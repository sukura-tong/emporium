package com.swust.emporium.service;

import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ProductExecution;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.Product;

import java.io.InputStream;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品操作业务逻辑层
 */
public interface ProductService {

    /**
     * 添加商品信息同时处理图片
     * @param product
     * @param thumbnail
     * @param productImgHolders
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(
            Product product,
            ImageHolder thumbnail,
            List<ImageHolder> productImgHolders
          ) throws ProductOperationException;

    ProductExecution queryProductById(long productId);

    /***
     * 更新商品数据
     * @param product
     * @return
     */
    ProductExecution updateProduct(Product product, ImageHolder thumbnail , List<ImageHolder> imageHolders);

    /**
     * 分页查询相关信息
     * @param product
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution queryProductList(Product product, int pageIndex, int pageSize);
}
