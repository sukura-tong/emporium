package com.swust.emporium.util.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.util.wechat.message.pojo.UserAccessToken;
import com.swust.emporium.util.wechat.message.pojo.WechartUser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 微信用户操作工具类
 */
public class WechartUserUtil {
    private static Logger logger = LoggerFactory.getLogger(WechartUserUtil.class);

    /**
     * 根据传入的密码获取UserAccessToken实体类
     * @param code
     * @return
     * @throws IOException
     */
    public static UserAccessToken getUserAccessToken(String code) throws IOException {

        String appId = "wx41553036ed413e7c";
        String appsecret = "e0237629347fc802457523f075ebfddc";

        // 拼接访问url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appId + "&secret=" + appsecret + "&code=" + code
                + "&grant_type=authorization_code";

        String tokenStr = WechartUtil.httpsRequest(url,"GET",null);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper mapper = new ObjectMapper();

        try {
            token = mapper.readValue(tokenStr, UserAccessToken.class);
        }catch (Exception e){
            logger.error("用户信息解析失败 ==> " + e.getMessage());
            e.printStackTrace();
        }

        if (token == null){
            logger.error("获取用户token失败");
        }

        return token;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public static WechartUser getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String buffer = WechartUtil.httpsRequest(url, "GET", null);

        JSONObject jsonObject = JSONObject.fromObject(buffer);

        WechartUser user = new WechartUser();
        user.setOpenId(openId);
        user.setNickName(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getInt("sex"));
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setCountry(jsonObject.getString("country"));
        user.setHeadimgurl(jsonObject.getString("headimgurl"));
        user.setPrivilege(null);
        return user;
    }
}
