//package com.swust.emporium.dao;
//
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.pojo.HeadLine;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class HeadLineDaoTest extends BaseTest {
//
//    @Autowired
//    private HeadLineDao headLineDao;
//
//    @Test
//    public void testAqueryHeadLine(){
//        HeadLine headLine = new HeadLine();
//        headLine.setEnableStatus(1);
//
//        List<HeadLine> headLines = headLineDao.queryHeadLine(headLine);
//        Assert.assertEquals(headLines.size(),1);
//    }
//}
