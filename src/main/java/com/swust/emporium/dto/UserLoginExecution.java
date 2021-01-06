package com.swust.emporium.dto;

import com.swust.emporium.enums.PersonInfoStateEnum;
import com.swust.emporium.pojo.PersonInfo;
import com.swust.emporium.pojo.UserLogin;
import lombok.Getter;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 用户登录操作封装类
 */
@Getter
public class UserLoginExecution {

    private int count;
    private UserLogin userLogin;
    private List<UserLogin> userLoginList;

    private int state;
    private String stateInfo;


    // 赋值
    public UserLoginExecution(PersonInfoStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public UserLoginExecution(PersonInfoStateEnum stateEnum, UserLogin userLogin){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userLogin = userLogin;
    }

    public UserLoginExecution(PersonInfoStateEnum stateEnum, List<UserLogin> userLoginList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
       this.userLoginList = userLoginList;
    }

}
