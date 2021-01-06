package com.swust.emporium.service;

import com.swust.emporium.dto.UserLoginExecution;
import com.swust.emporium.pojo.UserLogin;

public interface UserLoginService {
    /**
     * 用户登录查询标志
     * @param name
     * @param password
     * @return
     */
    UserLoginExecution getLoginStatus(String name, String password, int status);

    /***
     * 注册用户信息
     * @param userLogin
     * @return
     */
    UserLoginExecution registerUserInfo(UserLogin userLogin);

    /**
     * 更新用户密码
     * @param userLogin
     * @return
     */
    UserLoginExecution changeUserCodeInfo(UserLogin userLogin);

    /**
     * 查询信息通过用户ID
     * @param userId
     * @return
     */
    UserLoginExecution getUserInfoById(int userId);
}
