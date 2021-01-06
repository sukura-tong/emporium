package com.swust.emporium.web.frontend;

import com.swust.emporium.dto.ProductExecution;
import com.swust.emporium.exceptions.ProductOperationException;
import com.swust.emporium.service.ProductService;
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
 * 商品详情操作页面controller
 */
@Controller
@RequestMapping(value = "/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/showproduct", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> showProduct(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        long productId = HttpServletRequestUtils.getLong(request, "productId");

        if (!(productId > -1L)){
            modelMap.put("success",false);
            modelMap.put("errMsg","商品Id为空");
            return modelMap;
        }
        ProductExecution pe = null;
        try {
             pe = productService.queryProductById(productId);
        }catch (ProductOperationException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (pe != null){
            if (pe.getState() != 1){
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }

        modelMap.put("success", true);
        modelMap.put("product", pe.getProduct());
        return modelMap;
    }
}
