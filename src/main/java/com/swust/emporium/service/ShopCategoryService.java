package com.swust.emporium.service;

import com.swust.emporium.dto.ShopCategoryExecution;
import com.swust.emporium.pojo.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    public final static String SHOP_CATEGORY_KEY = "shopcategorylist";
    /**
     * 商品类别查询方法
     * @param shopCategoryCondition
     * @return
     */
    ShopCategoryExecution getShopCategoryList(ShopCategory shopCategoryCondition);

}
