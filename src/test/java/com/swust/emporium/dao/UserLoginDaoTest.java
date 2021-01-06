//package com.swust.emporium.dao;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.UserLogin;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class UserLoginDaoTest extends BaseTest {
//
//    @Autowired
//    private UserLoginDao userLoginDao;
//
//    @Test
//    @Ignore
//    public void testselLogin(){
//        String name = "小鹿";
//        String password = "xiaolu1996";
//        int status = 0;
//
//
//        UserLogin result = userLoginDao.getChooseByNameAndPassword(name, password,status);
//
//        System.out.println(result.getStatus());
//        System.out.println(result.getUname());
//        System.out.println(result.getUpwd());
//
//        if (result != null){
//            Assert.assertEquals(result.getStatus(),0);
//        }else {
//            System.out.println("error!");
//        }
//    }
//
//    @Test
//    @Ignore
//    public void testinsert(){
//        UserLogin userLogin = new UserLogin();
//
//        userLogin.setStatus(1);
//        userLogin.setUname("蝶恋");
//        userLogin.setUpwd("dielian1996");
//
//        int effectNum = userLoginDao.insertRegisterUserMessage(userLogin);
//
//        Assert.assertEquals(effectNum, 1);
//    }
//
//    @Test
//    @Ignore
//    public void testUpdate(){
//        UserLogin userLoginCondition = new UserLogin();
//
//        userLoginCondition.setUid(5);
//
//        userLoginCondition.setUpwd("yueyue1996");
//
//        int effectNum = userLoginDao.updateUserCodeById(userLoginCondition);
//        Assert.assertEquals(effectNum, 1);
//    }
//
//    @Test
//    public void testSelectByID(){
//        int uid = 1;
//        UserLogin info = userLoginDao.getUserInfoById(uid);
//        System.out.println(info.getUpwd());
//        System.out.println(info.getUname());
//    }
//}
