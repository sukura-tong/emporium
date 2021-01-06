package com.swust.emporium.web.wechart;

import com.swust.emporium.util.wechat.message.pojo.WechartUser;
import com.swust.emporium.util.wechat.util.WechartUserUtil;
import com.swust.emporium.util.wechat.message.pojo.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 获取关注微信公众号的微信用户信息接口
 */
@Controller
@RequestMapping(value = "wechat")
public class WechatLoginController {

    private static Logger logger = LoggerFactory.getLogger(WechatLoginController.class);

    @RequestMapping(value = "/logincheck", method = RequestMethod.GET)
    public String doGet(HttpServletRequest req, HttpServletResponse resp){
        logger.debug("login datas get...");
        // 获取微信公众号传输来的code，通过code获取access_token，从而获取用户信息
        String code = req.getParameter("code");
        String state = req.getParameter("state");

        WechartUser user = null;
        String openId = null;
        if (code != null){
            UserAccessToken token = null;
            try {
                // 通过code获取token对象
                token = WechartUserUtil.getUserAccessToken(code);
                // 通过token获取数据
                String accessToken = token.getAccessToken();
                openId = token.getOpenId();
                user = WechartUserUtil.getUserInfo(accessToken,openId);
                req.getSession().setAttribute("openId", openId);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // TODO...

        if (user != null){
            return "frontend/index";
        }else {
            return null;
        }
    }
}
