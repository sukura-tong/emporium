package com.swust.emporium.dao;

import com.swust.emporium.pojo.ProductImg;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品图片信息操作数据库Dao层
 */
public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 查询
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 删除数据
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
