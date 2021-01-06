//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.Product;
//import com.swust.emporium.pojo.ProductCategory;
//import com.swust.emporium.pojo.Shop;
//import com.swust.emporium.service.ProductService;
//import org.junit.Assert;
//import org.junit.FixMethodOrder;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author 雪瞳
// * @Slogan 忘时，忘物，忘我。
// * @Function
// * 商品操作测试类
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class ProductDaoTest extends BaseTest {
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Test
//    @Ignore
//    public void testAproductInsert(){
//        Product product = new Product();
//        product.setProductName("33");
//        product.setProductDesc("11");
//        product.setImgAddr("11");
//        product.setNormalPrice("111");
//        product.setPromotionPrice("22");
//        product.setPriority(11);
//        product.setCreateTime(new Date());
//        product.setLastEditTime(new Date());
//        product.setEnableStatus(1);
//
//        ProductCategory productCategory = new ProductCategory();
//        Shop shop = new Shop();
//        productCategory.setProductCategoryId(9L);
//        shop.setShopId(1L);
//
//        product.setProductCategory(productCategory);
//        product.setShop(shop);
//
//        int effectNum = productDao.insertProduct(product);
//        Assert.assertEquals(1, effectNum);
//    }
//
//    @Test
//    @Ignore
//    public void testBqueryProductById(){
//        Product product = productDao.queryProductById(10L);
//
//        System.out.println(product.getProductImgList().size());
////        System.out.println(sdf.format(createTime));/
//    }
//
//    @Test
//    @Ignore
//    public void testCupdateProduct(){
//
//        Product product = new Product();
//        product.setProductName("你梦1");
//        product.setProductDesc("111");
//        product.setImgAddr("11");
//        product.setNormalPrice("111");
//        product.setPromotionPrice("22");
//        product.setPriority(11);
//        product.setLastEditTime(new Date());
//        product.setEnableStatus(1);
//
//        ProductCategory productCategory = new ProductCategory();
//        Shop shop = new Shop();
//        productCategory.setProductCategoryId(9L);
//        shop.setShopId(20L);
//
//        product.setProductCategory(productCategory);
//        product.setShop(shop);
//
//        product.setProductId(18L);
//        int effectNum = productDao.updateProduct(product);
//        Assert.assertEquals(1, effectNum);
//    }
//
//    @Test
//    @Ignore
//    public void testQueryProductListCount(){
//        Product productCondition = new Product();
//
//        productCondition.setEnableStatus(1);
//
//        Shop shop = new Shop();
//        shop.setShopId(1L);
//        productCondition.setShop(shop);
//
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setProductCategoryId(9L);
//        productCondition.setProductCategory(productCategory);
//
//        productCondition.setProductName("更改");
//
//
//        List<Product> products = productDao.queryProductList(productCondition, 0, 1);
//        int effectNums = products.size();
//        System.out.println(effectNums);
//        Assert.assertEquals(effectNums, 1);
//    }
//
//    @Test
//    public void testSetProductCategoryIdToNull(){
//        long productCategoryId = 10L;
//        int effectNums = productDao.updateProductCategoryToNull(productCategoryId);
//        Assert.assertEquals(effectNums, 2);
//    }
//}
