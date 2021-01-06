//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.Product;
//import com.swust.emporium.pojo.ProductImg;
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
// * 商品图片操作测试方法
// */
//public class ProductImgDaoTest extends BaseTest {
//    @Autowired
//    private ProductImgDao productImgDao;
//
//    @Test
//    @Ignore
//    public void testAbatchInsert(){
//        List<ProductImg> productImgList = new ArrayList<>();
//        Product product = new Product();
//        product.setProductId(3L);
//
//        ProductImg productImg1 = new ProductImg();
//        productImg1.setImgAddr("1");
//        productImg1.setImgDesc("1");
//        productImg1.setPriority(1);
//        productImg1.setCreateTime(new Date());
//        productImg1.setProductId(product.getProductId());
//
//        ProductImg productImg2 = new ProductImg();
//        productImg2.setImgAddr("2");
//        productImg2.setImgDesc("2");
//        productImg2.setPriority(2);
//        productImg2.setCreateTime(new Date());
//        productImg2.setProductId(product.getProductId());
//
//        productImgList.add(productImg1);
//        productImgList.add(productImg2);
//
//        int effectNums = productImgDao.batchInsertProductImg(productImgList);
//        Assert.assertEquals(2,effectNums);
//
//    }
//
//    @Test
//    @Ignore
//    public void testQuertByID(){
//        List<ProductImg> productImgList = productImgDao.queryProductImgList(3);
//        Assert.assertEquals(2,productImgList.size());
//    }
//
//    @Test
//    public void testCdelete(){
//        long productId = 7L;
//        int i = productImgDao.deleteProductImgByProductId(productId);
//        Assert.assertEquals(i,2);
//    }
//}
