package com.swust.emporium.web.wechart;

import com.swust.emporium.util.wechat.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequestMapping(value = "wechat")
@Controller
public class WechartController {

    Logger logger = LoggerFactory.getLogger(WechartController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        logger.debug("wechart start...");
        //获取微信请求的加密签名 signature结合开发者填写的token参数和请求中的timestamp

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        // 通过校验 signature进行请求校验
        PrintWriter out = null;

        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)){
                logger.debug("get success...");
                out.println(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          if (out != null){
              out.close();
          }
        }

    }
}
