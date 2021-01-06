package com.swust.emporium.web.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dto.UserLoginExecution;
import com.swust.emporium.pojo.UserLogin;
import com.swust.emporium.service.UserLoginService;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendLoginController {


    @Autowired
    private UserLoginService userLoginService;

    private static Logger logger = LoggerFactory.getLogger(FrontendLoginController.class);


    @RequestMapping(value = "/getoperationlogin", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOperationLogin(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        String name = HttpServletRequestUtils.getString(request, "name");
        String password = HttpServletRequestUtils.getString(request, "password");
        String select  = HttpServletRequestUtils.getString(request, "userselect");

        int status = 0;

        if (select != null){
            if ("普通用户".equals(select)){
                status = 0;
            }else if ("店家".equals(select)){
                status = 1;
            }else if ("管理员".equals(select)){
                status = 2;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "基本登录选项选择错误");
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本登录选项为空");
            return modelMap;
        }


        if (name == null || password == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本登录信息为空");
            return modelMap;
        }

        UserLoginExecution ule = null;
        try {
             ule = userLoginService.getLoginStatus(name, password, status);
        }catch (RuntimeException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (ule != null){
            if (ule.getState() != 1){
                modelMap.put("success", false);
                modelMap.put("errMsg", ule.getStateInfo());
                return modelMap;
            }else {
                modelMap.put("success", true);
                // 将用户信息保存到session中
                HttpSession session = request.getSession();
                session.setAttribute("userLogin", ule.getUserLogin());

                modelMap.put("choosetip",ule.getUserLogin().getStatus());
                modelMap.put("userLogin",ule.getUserLogin());
                modelMap.put("userId",ule.getUserLogin().getUid());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询结果为空");
            return modelMap;
        }
    }

    @RequestMapping(value = "/registerusermessage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerUserMessage(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // TODO... 验证码校验

        String userMessageStr = HttpServletRequestUtils.getString(request, "userMessageStr");
        ObjectMapper mapper = new ObjectMapper();
        UserLogin userLoginCondition = null;
        try {
            userLoginCondition = mapper.readValue(userMessageStr, UserLogin.class);
        } catch (IOException e) {
            logger.error("err ==> " + e.getMessage());
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (userLoginCondition == null || userLoginCondition.getUpwd() == null || userLoginCondition.getUname() == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本注册信息为空");
            logger.error("err ==> " + "基本注册信息为空");
            return modelMap;
        }

        // register
        UserLoginExecution ule = null;
        try {
             ule = userLoginService.registerUserInfo(userLoginCondition);
        }catch (Exception e){
            logger.error("err ==> " + e.getMessage());
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (ule == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "注册失败");
            logger.error("err ==> " + "注册失败");
            return modelMap;
        }

        if (ule.getState() != 1){
            modelMap.put("success", false);
            modelMap.put("errMsg", ule.getStateInfo());
            logger.error("err ==> " + ule.getStateInfo());
            return modelMap;
        }

        modelMap.put("success", true);
        logger.info("success ==> " + "注册成功");
        return modelMap;
    }

    /**
     * http://localhost:8888/emporium/frontend/updateusercodeinfo?userId=5&oldCode=yueyue1996&newCodeOne=yuewu1996&newCodeTwo=yuewu1996
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateusercodeinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> updateUserCodeInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        //TODO... 验证码校验

        String oldCode = HttpServletRequestUtils.getString(request, "oldCode");
        String newCodeOne = HttpServletRequestUtils.getString(request, "newCodeOne");
        String newCodeTwo = HttpServletRequestUtils.getString(request, "newCodeTwo");
//        int userId = HttpServletRequestUtils.getInt(request, "userId");

        UserLogin userLogin = (UserLogin) request.getSession().getAttribute("userLogin");
        int userId = userLogin.getUid();

        if (oldCode == null || newCodeOne == null || newCodeTwo == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "基本信息为空");
            logger.error("err ==> " + "基本信息为空");
            return modelMap;
        }

        // 比较两次输入密码是否一致
        if (!newCodeOne.equals(newCodeTwo)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "两次输入密码不一致");
            logger.error("err ==> " + "两次输入密码不一致");
            return modelMap;
        }

        // 比较历史密码是否相同
        UserLoginExecution info = null;
        try {
             info = userLoginService.getUserInfoById(userId);
        }catch (Exception e){
            logger.error("err ==> " + e.getMessage());
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        if (info == null){
            logger.error("err ==> " + "用户信息不存在");
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户信息不存在");
            return modelMap;
        }
        if (info.getState() != 1){
            modelMap.put("success", false);
            modelMap.put("errMsg", info.getStateInfo());
            logger.error("err ==> " + info.getStateInfo());
            return modelMap;
        }

        if (info.getUserLogin() != null && info.getUserLogin().getUpwd() != null){
            if (!oldCode.equals(info.getUserLogin().getUpwd())){
                modelMap.put("success", false);
                modelMap.put("errMsg", "历史密码错误");
                logger.error("err ==> " + "历史密码错误");
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息为空");
            logger.error("err ==> " + "查询信息为空");
            return modelMap;
        }

        // 更新用户密码
        UserLogin userLoginCondition = new UserLogin();
        userLoginCondition.setUid(userId);
        userLoginCondition.setUpwd(newCodeOne);

        UserLoginExecution ule = null;
        try {
             ule = userLoginService.changeUserCodeInfo(userLoginCondition);
        }catch (Exception e){
            logger.error("err ==> " + e.getMessage());
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (ule == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码更新失败");
            logger.error("err ==> " + "密码更新失败");
            return modelMap;
        }

        if (ule.getState() != 1){
            modelMap.put("success", false);
            modelMap.put("errMsg", ule.getStateInfo());
            logger.error("err ==> " + ule.getStateInfo());
            return modelMap;
        }

        modelMap.put("success", true);
        logger.info("success ==> " + "用户密码更新成功");
        return modelMap;
    }
}
