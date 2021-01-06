//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.ShopCategory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class ShopCategoryDaoTest extends BaseTest {
//
//    @Autowired
//    private ShopCategoryDao shopCategoryDao;
//
//    @Test
//    public void testQueryShopCategoryDao(){
//        ShopCategory parent = new ShopCategory();
//        parent.setShopCategoryId(10L);
//        ShopCategory shopCategory = new ShopCategory();
//        shopCategory.setParent(parent);
//        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(shopCategory);
//        Assert.assertEquals(shopCategories.size(),2);
////        System.out.println(shopCategories.size());
//    }
//}
