//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.ProductCategory;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author 雪瞳
// * @Slogan 忘时，忘物，忘我。
// * @Function
// * 产品类别数据库操作测试
// */
//public class ProductCategoryDaoTest extends BaseTest {
//
//    @Autowired
//    private ProductCategoryDao productCategoryDao;
//
//    @Test
//    @Ignore
//    public void testQueryProductCategory(){
//
//        List<ProductCategory> productCategories = productCategoryDao.queryProductCategory(1L);
//        Assert.assertEquals(productCategories.size(), 4);
//
//    }
//
//    @Test
//    @Ignore
//    public void testDeleteProductCategory(){
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setShopId(1L);
//        productCategory.setProductCategoryId(1L);
//        int i = productCategoryDao.deleteProductCategory(productCategory);
//        System.out.println(i);
//    }
//
//    @Test
//    public void testBatchInsertProductCategory(){
//        ProductCategory productCategory1 = new ProductCategory();
//        productCategory1.setShopId(1L);;
//        productCategory1.setCreateTime(new Date());
//        productCategory1.setPriority(1);
//        productCategory1.setProductCategoryName("lu");
//
//        ProductCategory productCategory2 = new ProductCategory();
//        productCategory2.setShopId(1L);;
//        productCategory2.setCreateTime(new Date());
//        productCategory2.setPriority(2);
//        productCategory2.setProductCategoryName("xue");
//
//        List<ProductCategory> list = new ArrayList<>();
//        list.add(productCategory1);
//        list.add(productCategory2);
//
//        int effectNums = productCategoryDao.batchInsertProductCategory(list);
//
//        Assert.assertEquals(2,effectNums);
//    }
//}
