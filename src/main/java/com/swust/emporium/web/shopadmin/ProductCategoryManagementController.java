package com.swust.emporium.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dto.ProductCategoryExecution;
import com.swust.emporium.enums.ProductCategoryStateEnum;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.pojo.ProductCategory;
import com.swust.emporium.pojo.Shop;
import com.swust.emporium.service.ProductCategoryService;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别管理controller层
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getProductCategoryList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();


        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Long shopId = currentShop.getShopId();

        ProductCategoryExecution pce = null;

        try {
            pce = productCategoryService.queryGetProductCategoryList(shopId);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (pce.getState() == 1){
            modelMap.put("success", true);
            modelMap.put("productCategoryList", pce.getProductCategoryList());
            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", pce.getStateInfo());
            return modelMap;
        }
    }


    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();
        // 获取信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Long shopId = currentShop.getShopId();
        if (productCategoryId == null || productCategoryId <= 0){
                modelMap.put("success",false);
                modelMap.put("errMsg", "请至少输入要删除的商品类别");
                return modelMap;
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setShopId(shopId);
        productCategory.setProductCategoryId(productCategoryId);
        ProductCategoryExecution pce = null;
        try {
            pce = productCategoryService.deleteProductCategory(productCategory);
        }catch (ProductOperationException e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (pce != null){
            if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
                return modelMap;
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg", pce.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "err ==> 删除数据失败");
            return modelMap;
        }
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategorys(
            @RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();
        // 获取信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Long shopId = currentShop.getShopId();

       for (ProductCategory pc : productCategoryList){
           pc.setShopId(shopId);
       }

       ProductCategoryExecution pce = null;
       if (productCategoryList != null && productCategoryList.size() > 0){
           try {
               pce = productCategoryService.batchInsertProductCategoryList(productCategoryList);
               if (pce.getState() != 1){
                   modelMap.put("success",false);
                   modelMap.put("errMsg", pce.getStateInfo());
                   return modelMap;
               }else {
                   modelMap.put("success",true);
                   return modelMap;
               }
           }catch (ProductOperationException e){
               modelMap.put("success",false);
               modelMap.put("errMsg", e.getMessage());
               return modelMap;
           }
       }else {
           modelMap.put("success",false);
           modelMap.put("errMsg", "err ==> 请至少输入一个商品类别");
           return modelMap;
       }

    }

}
