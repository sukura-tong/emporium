package com.swust.emporium.web.frontend;

import com.swust.emporium.dto.ProductCategoryExecution;
import com.swust.emporium.dto.ProductExecution;
import com.swust.emporium.exceptions.ProductCategoryOpertionException;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.exceptions.ShopOperationException;
import com.swust.emporium.pojo.Product;
import com.swust.emporium.pojo.ProductCategory;
import com.swust.emporium.pojo.Shop;
import com.swust.emporium.service.*;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺详情页controller层
 */
@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及店铺下面的商品列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listdetailshoppageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listDetailShopPageInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtils.getLong(request, "shopId");

        if (shopId == -1L){
            modelMap.put("success",false);
            modelMap.put("errMsg", "店铺Id信息为空");
            return modelMap;
        }

        Shop shopCurrent = null;
        try {
            shopCurrent = shopService.getByShopId(shopId);
        }catch (ShopOperationException e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        ProductCategoryExecution pce = null;
        try {
             pce = productCategoryService.queryGetProductCategoryList(shopId);
        }catch (ProductOperationException e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (pce != null){
            if (pce.getState() != 1){
                modelMap.put("success",false);
                modelMap.put("errMsg", pce.getStateInfo());
                return modelMap;
            }
        }else {
            throw new ProductCategoryOpertionException("err ==> " + "商品类别查询异常");
        }


        // 返回前端
        modelMap.put("success",true);
        modelMap.put("shop",shopCurrent);
        modelMap.put("productCategoryList",pce.getProductCategoryList());


        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    public Map<String, Object> listProductsByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        int pageIndex = HttpServletRequestUtils.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
        long shopId = HttpServletRequestUtils.getLong(request, "shopId");

        if (!(pageIndex > -1L && shopId > -1L && pageSize > -1L)){
            modelMap.put("success",false);
            modelMap.put("errMsg", "基本查询信息为空");
            return modelMap;
        }

        // 尝试获取查询内容
        String productName = HttpServletRequestUtils.getString(request, "productName");
        long productCategoryId = HttpServletRequestUtils.getLong(request, "productCategoryId");

        Product productCondition = null;
        productCondition = getSearchConditon(shopId, productName, productCategoryId);

        ProductExecution pe = null;
        try {
            pe = productService.queryProductList(productCondition, pageIndex, pageSize);
        }catch (ProductOperationException e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (pe != null){
            if (pe.getState() != 1){
                modelMap.put("success",false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        }else {
            throw new ProductOperationException("err ==> " + "商品信息查询异常");
        }

        modelMap.put("success",true);
        modelMap.put("productList",pe.getProductList());
        modelMap.put("count",pe.getCount());

        return modelMap;
    }

    private static Product getSearchConditon(long shopId, String productName, long productCategoryId){
        Product productCondition = new Product();

        if (shopId != -1L){
            Shop shop = new Shop();
            shop.setShopId(shopId);
            productCondition.setShop(shop);
        }

        if(productName != null && !"".equals(productName)){
            productCondition.setProductName(productName);
        }

        if (productCategoryId != -1L){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        // 只允许显示上架状态的商品
        productCondition.setEnableStatus(1);

        return productCondition;
    }
}
