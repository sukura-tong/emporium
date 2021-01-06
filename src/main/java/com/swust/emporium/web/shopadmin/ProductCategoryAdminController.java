package com.swust.emporium.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 *将请求进行转发
 */
@Controller
@RequestMapping(value = "/shopadmin", method = RequestMethod.GET)
public class ProductCategoryAdminController {

    @RequestMapping(value = "/productcategorymanage")
    public String productCategoryManage(){
        return "shop/productcategorymanage";
    }

}
