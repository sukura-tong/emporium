package com.swust.emporium.service.impl;

import com.swust.emporium.dao.UserLoginDao;
import com.swust.emporium.dto.UserLoginExecution;
import com.swust.emporium.enums.PersonInfoStateEnum;
import com.swust.emporium.pojo.UserLogin;
import com.swust.emporium.service.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginDao userLoginDao;

    private static Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Override
    public UserLoginExecution getLoginStatus(String name, String password, int status) {

        UserLoginExecution ule = null;

        // 非空判断
        if (name == null || "".equals(name) || password == null || "".equals(password)){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE);
            return ule;
        }

        UserLogin login = null;
        try {
            login = userLoginDao.getChooseByNameAndPassword(name, password, status);
        }catch (RuntimeException exception){
            throw new RuntimeException(exception.getMessage());
        }

        if (login != null){
            ule = new UserLoginExecution(PersonInfoStateEnum.SUCCESS, login);
        }else {
            ule = new UserLoginExecution(PersonInfoStateEnum.INNER_ERROR);
        }
        return ule;
    }


    @Override
    @Transactional
    public UserLoginExecution registerUserInfo(UserLogin userLogin) {
        UserLoginExecution ule = null;

        if (userLogin == null || userLogin.getUname() == null || userLogin.getUpwd() == null){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
            return ule;
        }

        if (userLogin.getStatus() > 2 || userLogin.getStatus() < 0){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
        }

        // 注册信息
        int effectNum = 0;
        try {
            effectNum = userLoginDao.insertRegisterUserMessage(userLogin);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("err ==> " + e.getMessage());
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
        }

        if (effectNum != 1){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
            logger.error("err ==> " + "信息插入失败");
        }

        ule = new UserLoginExecution(PersonInfoStateEnum.SUCCESS, userLogin);
        
        return ule;
    }

    @Override
    @Transactional
    public UserLoginExecution changeUserCodeInfo(UserLogin userLogin) {
        UserLoginExecution ule = null;

        if (userLogin == null || userLogin.getUpwd() == null || userLogin.getUid() < 0 ){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
            return ule;
        }

        // 更新信息
        int effectNum = 0;
        try {
            effectNum = userLoginDao.updateUserCodeById(userLogin);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("err ==> " + e.getMessage());
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
        }

        if (effectNum != 1){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE, userLogin);
            logger.error("err ==> " + "用户密码更新失败");
        }

        ule = new UserLoginExecution(PersonInfoStateEnum.SUCCESS, userLogin);

        return ule;
    }

    @Override
    public UserLoginExecution getUserInfoById(int userId) {
        UserLoginExecution ule = null;
        if (userId < 0){
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE);
            return ule;
        }
        UserLogin userInfo = null;
        try {
             userInfo = userLoginDao.getUserInfoById(userId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("err ==> " + e.getMessage());
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE);
            return ule;
        }

        if (userInfo == null){
            logger.error("err ==> " + "查询失败，用户信息为空");
            ule = new UserLoginExecution(PersonInfoStateEnum.NULL_MESSAGE);
            return ule;
        }

        ule = new UserLoginExecution(PersonInfoStateEnum.SUCCESS, userInfo);

        return ule;
    }
}
