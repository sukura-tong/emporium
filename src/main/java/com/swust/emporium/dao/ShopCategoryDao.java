package com.swust.emporium.dao;

import com.swust.emporium.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商铺类别Dao层
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

//    /***
//     * 查询父类及其子类
//     * @param shopCategoryCondition
//     * @return
//     */
//    List<ShopCategory> queryParentAndChild(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
