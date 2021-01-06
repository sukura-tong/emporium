//package com.swust.emporium.serive;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.dto.ImageHolder;
//import com.swust.emporium.dto.ShopExecution;
//import com.swust.emporium.enums.ShopStateEnum;
//import com.swust.emporium.pojo.Area;
//import com.swust.emporium.pojo.PersonInfo;
//import com.swust.emporium.pojo.Shop;
//import com.swust.emporium.pojo.ShopCategory;
//import com.swust.emporium.service.ShopService;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.Date;
//
//
//public class ShopServiceTest extends BaseTest {
//
//    @Autowired
//    private ShopService shopService;
//
//    @Test
//    public void testGetShopList(){
//        Shop shopCondition = new Shop();
//        int pageIndex = 1;
//        int pageSize = 1;
//
//        ShopExecution shopList = shopService.getShopList(shopCondition, pageIndex, pageSize);
//        System.out.println(shopList.getStateInfo());
//    }
//
//
//    @Test
//    @Ignore
//    public void modifyTestShop() throws FileNotFoundException {
//        Shop shop = shopService.getByShopId(22L);
//        shop.setShopName("xiugai");
//        String path = "F:\\emporium\\img\\2.jpg";
//        File file = new File(path);
//
//
//        String fileName = file.getName();
//        InputStream shopImgInputStream = new FileInputStream(file);
//        ShopExecution shopExecution = shopService.modifyShop(shop, new ImageHolder());
//        System.out.println(shopExecution.getStateInfo());
//
//    }
//
//
//
//
//
//    @Test
//    @Ignore
//    public void addShop() throws FileNotFoundException {
//        Shop shop = new Shop();
//        Area area = new Area();
//        PersonInfo owner = new PersonInfo();
//        ShopCategory shopCategory = new ShopCategory();
//
//        owner.setUserId(1L);
//        area.setAreaId(2);
//        shopCategory.setShopCategoryId(1L);
//
//        shop.setOwner(owner);
//        shop.setShopCategory(shopCategory);
//        shop.setArea(area);
//
//        shop.setShopName("小鹿3");
//        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
//        shop.setShopDesc("test3.service");
//        shop.setShopAddr("test3.service");
//        shop.setPriority(0);
//        shop.setCreateTime(new Date());
//        shop.setPhone("123");
//        shop.setAdvice("审核中..");
//
//        String path = "F:\\emporium\\img\\1.jpg";
//        File file = new File(path);
//        InputStream shopImg = new FileInputStream(file);
//
//        ImageHolder imageHolder = new ImageHolder();
//
//        ShopExecution shopExecution = shopService.addShop(shop,imageHolder);
//        System.out.println(shopExecution.getStateInfo());
//        Assert.assertEquals(shopExecution.getState(),ShopStateEnum.CHECK.getState());
//    }
//
//
//}
