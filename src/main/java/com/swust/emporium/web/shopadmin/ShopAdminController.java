package com.swust.emporium.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 实现请求转发
 * 防止用户直接访问html文件
 * 访问静态资源
 *
 * 当需要访问webInf 下面的 html文件时 需要使用该路径路由
 * 通过SpringMvc进行请求转发 通过视图解析器进行访问路径拼接
 */
@Controller
@RequestMapping(value = "shopadmin", method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "/shop/shopmanagement";
    }


}
