//package com.swust.emporium.serive;
//
//import com.swust.emporium.BaseTest;
//import com.swust.emporium.dto.HeadLineExecution;
//import com.swust.emporium.pojo.HeadLine;
//import com.swust.emporium.service.HeadLineService;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class HeadLineServiceTest extends BaseTest {
//
//    @Autowired
//    private HeadLineService headLineService;
//
//    @Test
//    @Ignore
//    public void testqueryList(){
//        HeadLine headLine = new HeadLine();
//        headLine.setEnableStatus(1);
//        HeadLineExecution headLineLists = headLineService.getHeadLineLists(headLine);
//        System.out.println(headLineLists.getHeadLineList().size());
//    }
//}
