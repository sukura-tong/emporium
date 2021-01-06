package com.swust.emporium.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 核验效验码
 */
public class CodeUtils {
    public static boolean checkVerifyCode(HttpServletRequest request){
        // 输入验证码
        String verifyCodeInput = HttpServletRequestUtils.getString(request,"verifyCodeActual");
        String verifyCodeActual = (String) request
                .getSession()
                .getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (verifyCodeInput == null || verifyCodeActual.equals(verifyCodeInput)){
            return false;
        }
        return true;
    }
}
