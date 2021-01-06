//package com.swust.emporium.serive;
//
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.dto.ImageHolder;
//import com.swust.emporium.dto.ProductExecution;
//import com.swust.emporium.pojo.Product;
//import com.swust.emporium.pojo.ProductCategory;
//import com.swust.emporium.pojo.Shop;
//import com.swust.emporium.service.ProductService;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//
//public class ProductServiceTest extends BaseTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    @Ignore
//    public void testAProductService() throws FileNotFoundException {
//        Product product = new Product();
//        product.setProductName("test1");
//        product.setProductDesc("test");
//        product.setNormalPrice("test");
//        product.setPromotionPrice("test");
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
//        // 缩略图
//
//        ImageHolder imageHolder1 = new ImageHolder();
//        String pathOne = "F:\\emporium\\img\\1.jpg";
//        File one = new File(pathOne);
//        InputStream inputStream1 = new FileInputStream(one);
//        imageHolder1.setImage(inputStream1);
//        imageHolder1.setImageName(one.getName());
//
//        ImageHolder imageHolder2 = new ImageHolder();
//        String pathTwo = "F:\\emporium\\img\\2.jpg";
//        File two = new File(pathTwo);
//        InputStream inputStream2 = new FileInputStream(two);
//        imageHolder2.setImage(inputStream2);
//        imageHolder2.setImageName(two.getName());
//
//
////        ImageHolder imageHolder3 = new ImageHolder();
////        String pathThree = "F:\\emporium\\img\\2.jpg";
////        File three = new File(pathThree);
////        InputStream inputStream3 = new FileInputStream(two);
////        imageHolder3.setImage(inputStream3);
////        imageHolder3.setImageName(three.getName());
//
//        // 详情图片
//        List<ImageHolder> list = new ArrayList<>();
////        list.add(imageHolder1);
//        list.add(imageHolder2);
//
//        ProductExecution execution = productService.addProduct(product, imageHolder1, list);
//
//        Assert.assertEquals(execution.getState(), 1);
//    }
//
//    @Test
//    @Ignore
//    public void testUpdateProduct() throws FileNotFoundException {
//        Product product = new Product();
//        product.setProductName("22");
//        product.setProductDesc("22");
//        product.setNormalPrice("22");
//        product.setPromotionPrice("22");
//        product.setPriority(11);
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
//        product.setProductId(15L);
//        // 缩略图
//
//        ImageHolder imageHolder1 = new ImageHolder();
//        String pathOne = "F:\\emporium\\img\\1.jpg";
//        File one = new File(pathOne);
//        InputStream inputStream1 = new FileInputStream(one);
//        imageHolder1.setImage(inputStream1);
//        imageHolder1.setImageName(one.getName());
//
//        ImageHolder imageHolder2 = new ImageHolder();
//        String pathTwo = "F:\\emporium\\img\\2.jpg";
//        File two = new File(pathTwo);
//        InputStream inputStream2 = new FileInputStream(two);
//        imageHolder2.setImage(inputStream2);
//        imageHolder2.setImageName(two.getName());
//
//
//        ImageHolder imageHolder3 = new ImageHolder();
//        String pathThree = "F:\\emporium\\img\\2.jpg";
//        File three = new File(pathThree);
//        InputStream inputStream3 = new FileInputStream(two);
//        imageHolder3.setImage(inputStream3);
//        imageHolder3.setImageName(three.getName());
//
//        // 详情图片
//        List<ImageHolder> list = new ArrayList<>();
//        list.add(imageHolder1);
//        list.add(imageHolder2);
//
//        ProductExecution e = productService.updateProduct(product, imageHolder3, list);
//
//        System.out.println(e.getState());
//    }
//
//    @Test
//    public void testQueryListServicce(){
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
////        productCondition.setProductName("更改");
//
//        ProductExecution productExecution = productService.queryProductList(productCondition, 0, 10);
//        System.out.println(productExecution.getStateInfo());
//    }
//}
