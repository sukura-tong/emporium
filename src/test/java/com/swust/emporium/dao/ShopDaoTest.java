//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.Area;
//import com.swust.emporium.pojo.PersonInfo;
//import com.swust.emporium.pojo.Shop;
//import com.swust.emporium.pojo.ShopCategory;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class ShopDaoTest extends BaseTest {
//
//    @Autowired
//    private ShopDao shopDao;
//
//
//    @Test
//    @Ignore // 相当于忽视这个方法
//    public void testShopDao(){
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
//        shop.setEnableStatus(1);
//        shop.setAdvice("test");
//        shop.setCreateTime(new Date());
//        shop.setPhone("123");
//        shop.setPriority(1);
//        shop.setShopName("雪瞳");
//        shop.setShopDesc("test");
//        shop.setShopAddr("test");
//
//        int i = shopDao.insertSHop(shop);
//        assertEquals(1,i);
//    }
//
//    @Test
//    @Ignore
//    public void testupdateShopDao(){
//        Shop shop = new Shop();
//        Area area = new Area();
//        PersonInfo owner = new PersonInfo();
//        ShopCategory shopCategory = new ShopCategory();
//
//        owner.setUserId(1L);
//        area.setAreaId(2);
//        shopCategory.setShopCategoryId(1L);
//
//        shop.setShopId(1L);
//        shop.setOwner(owner);
//        shop.setShopCategory(shopCategory);
//        shop.setArea(area);
//
//        shop.setEnableStatus(1);
//        shop.setAdvice("test");
//        shop.setLastEditTime(new Date());
//        shop.setPhone("123");
//        shop.setPriority(1);
//        shop.setShopName("雪瞳");
//        shop.setShopDesc("111");
//        shop.setShopAddr("111");
//
//        int i = shopDao.updateShop(shop);
//        assertEquals(1,i);
//    }
//
//
//   @Test
////   @Ignore
//    public void testQueryByShopId(){
//        long shopId = 1;
//        Shop shop = shopDao.queryByShopId(shopId);
//        System.out.println(shop.getShopCategory().getShopCategoryName());
//       System.out.println(shop.getCreateTime());
//    }
//
//    @Test
//    @Ignore
//    public void testQueryShopList(){
//        Shop shopCondition = new Shop();
//        Area area = new Area();
//        PersonInfo owner = new PersonInfo();
//        ShopCategory shopCategory = new ShopCategory();
//
////        area.setAreaId(2);
////        owner.setUserId(1L);
////        shopCategory.setShopCategoryId(14L);
//
////        shopCondition.setShopName("的");
//        shopCondition.setShopCategory(shopCategory);
//        shopCondition.setOwner(owner);
//        shopCondition.setArea(area);
//
////        int rowIndex = 0;
////        int pageSize = 2;
//
//        ShopCategory parent = new ShopCategory();
//        parent.setShopCategoryId(11L);
//        shopCategory.setParent(parent);
//
////        List<Shop> shops = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
//        int count = shopDao.queryShopCount(shopCondition);
////        System.out.println(shops.get(0).getAdvice());
////        Assert.assertEquals(shops.size(), 2);
////        Assert.assertEquals(count, 1);
////        System.out.println(shops.size());
//        System.out.println(count);
//    }
//
//}
