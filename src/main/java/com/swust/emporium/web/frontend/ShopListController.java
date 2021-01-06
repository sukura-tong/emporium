package com.swust.emporium.web.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dao.ShopDao;
import com.swust.emporium.dto.ShopCategoryExecution;
import com.swust.emporium.dto.ShopExecution;
import com.swust.emporium.exceptions.ShopCategoryOpertionException;
import com.swust.emporium.exceptions.ShopOperationException;
import com.swust.emporium.pojo.Area;
import com.swust.emporium.pojo.Shop;
import com.swust.emporium.pojo.ShopCategory;
import com.swust.emporium.service.AreaService;
import com.swust.emporium.service.ShopCategoryService;
import com.swust.emporium.service.ShopService;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺管理信息controller
 */
@Controller
@RequestMapping(value = "/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;


    /***
     * 返回商店列表页里的ShopCategory列表 以及区域信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    public Map<String, Object> listShopPageInfo(HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();
        // 尝试从前端获取parentId
        long parentId = HttpServletRequestUtils.getLong(request, "parentId");


        ShopCategoryExecution sce = null;

        if (parentId != -1){
            // 存在parentId则取出一级shopCategory下的二级shopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                sce =  shopCategoryService.getShopCategoryList(shopCategoryCondition);

            }catch (ShopCategoryOpertionException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        }else {
            // parentId不存在
             try {

                 sce =  shopCategoryService.getShopCategoryList(null);
             }catch (ShopCategoryOpertionException e){
                 modelMap.put("success", false);
                 modelMap.put("errMsg", e.getMessage());
                 return modelMap;
             }
        }
        // 查到了才添加
        if (sce != null){
            if (sce.getState() == 1){
                modelMap.put("shopCategoryList", sce.getShopCategoryList());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", sce.getStateInfo());
                return modelMap;
            }
        }else {
            // 说明没有信息
            modelMap.put("shopCategoryList", null);
        }

        /// 查询区域信息
        List<Area> areaList = new ArrayList<>();

        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        }catch (RuntimeException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
    }

    /***
     * 获取指定查询条件下的店铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShops(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // 获取页码和查询条数
        int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");

        ShopExecution se = null;

        if (pageIndex > -1 && pageSize > -1){
            //尝试获取查询条件
            long shopCategoryId = HttpServletRequestUtils.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtils.getInt(request, "areaId");
            long parentId = HttpServletRequestUtils.getLong(request, "parentId");
            String shopName = HttpServletRequestUtils.getString(request, "shopName");
            // 获取组合查询条件
            Shop shopCondition = getQueryCondition(shopName, parentId, shopCategoryId, areaId);
            se = shopService.getShopList(shopCondition, pageIndex, pageSize);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本查询信息为空");
            return modelMap;
        }

        if (se != null){
            if (se.getState() == 1){
                modelMap.put("shopList", se.getShopList());
                modelMap.put("count", se.getCount());
                modelMap.put("success",true);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
                return modelMap;
            }
        }else {
            // 说明没有信息
            modelMap.put("shopList", null);
            return modelMap;
        }
    }

    private Shop getQueryCondition(String shopName, long parentId, long shopCategoryId, int areaId){
        Shop shop = new Shop();

        if (shopName != null){
            shop.setShopName(shopName);
        }

        if (parentId != -1L){
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            ShopCategory child = new ShopCategory();
            child.setParent(parent);

            shop.setShopCategory(child);
        }

        if (shopCategoryId != -1L){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shop.setShopCategory(shopCategory);
        }

        if (areaId != -1L){
            Area area = new Area();
            area.setAreaId(areaId);
            shop.setArea(area);
        }

        return shop;
    }
}
