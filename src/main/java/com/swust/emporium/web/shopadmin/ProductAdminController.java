package com.swust.emporium.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 产品信息controller层
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductAdminController {

    @RequestMapping(value = "/productmanagement")
    public String productManage(){
        return "shop/productmanage";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }
}
