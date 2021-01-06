package com.swust.emporium.interceptor.shopadmin;

import com.swust.emporium.pojo.Shop;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 操作验证拦截器
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取当前选择的店铺
        Object currentShopObj = request.getSession().getAttribute("currentShop");
        Shop currentShop = null;
        if (currentShopObj != null){
            currentShop = (Shop)currentShopObj;
        }

        // 从session中获取当前用户可以操作的店铺列表
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");

        if (currentShop != null && shopList != null){
            // 遍历可以操作的店铺列表
            for (Shop shop : shopList){
                if (shop.getShopId() == currentShop.getShopId()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
