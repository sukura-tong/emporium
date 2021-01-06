package com.swust.emporium.web.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dto.HeadLineExecution;
import com.swust.emporium.dto.ShopCategoryExecution;
import com.swust.emporium.exceptions.HeadLineOperationException;
import com.swust.emporium.pojo.HeadLine;
import com.swust.emporium.pojo.ShopCategory;
import com.swust.emporium.service.HeadLineService;
import com.swust.emporium.service.ShopCategoryService;
import com.swust.emporium.util.HttpServletRequestUtils;
import com.swust.emporium.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 头条展示controller层
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoryService shopCategoryService;


    /**
     * 初始化前端及主页展示信息
     *  获取一级店铺类别以及头条列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);

        HeadLineExecution hle = null;
        ShopCategoryExecution sce = null;
        try {
            hle = headLineService.getHeadLineLists(headLine);
            sce = shopCategoryService.getShopCategoryList(null);

        }catch (HeadLineOperationException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

      if (hle.getState() == 1 && sce.getState() == 1){
          List<HeadLine> headLineList = hle.getHeadLineList();
          List<ShopCategory> shopCategoryList = sce.getShopCategoryList();

          // 处理图片路径
          for (HeadLine elem : headLineList){
              String lineImg = elem.getLineImg();
              String seperator = PathUtils.replaceSeperator(lineImg);
              elem.setLineImg(seperator);
          }

          for (ShopCategory elem : shopCategoryList){
              String img = elem.getShopCategoryImg();
              String seperator = PathUtils.replaceSeperator(img);
              elem.setShopCategoryImg(seperator);
          }

          modelMap.put("success", true);
          modelMap.put("shopCategoryList", shopCategoryList);
          modelMap.put("headLineList",headLineList);
          return modelMap;
      }else {
          modelMap.put("success", false);
          modelMap.put("errMsg","信息查询失败");
          return modelMap;
      }

    }
}
