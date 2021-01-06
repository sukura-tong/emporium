package com.swust.emporium.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendAdminController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "frontend/index";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    public String showShopList(){
        return "frontend/shoplist";
    }


    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    public String shopDetail(){
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    public String productDetail(){
        return "frontend/productdetail";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "frontend/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "frontend/registered";
    }

    @RequestMapping(value = "/modifycode", method = RequestMethod.GET)
    public String modifyCode(){
        return "frontend/modifycode";
    }
}
