package com.swust.emporium.dao;

import com.swust.emporium.pojo.UserLogin;
import org.apache.ibatis.annotations.Param;

public interface UserLoginDao {

    /**
     * 查询数据库是否具备登录信息
     * @param uName
     * @param pwd
     * @return
     */
    UserLogin getChooseByNameAndPassword(@Param("name") String uName, @Param("password")String pwd, @Param("status")int status);

    /***
     * 注册用户信息
     * @param userLogin
     * @return
     */
    int insertRegisterUserMessage(UserLogin userLogin);

    /**
     * 更新用户密码
     * @param userLoginCondition
     * @return
     */
    int updateUserCodeById(@Param("userLoginCondition") UserLogin userLoginCondition);

    /**
     * 根据用户ID查询用户信息
     * @param uid
     * @return
     */
    UserLogin getUserInfoById(@Param("uid") int uid);
}
