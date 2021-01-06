package com.swust.emporium.enums;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 用户信息登录枚举类
 */
public enum PersonInfoStateEnum {

    SUCCESS(1, "查询成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_MESSAGE(-1002,"信息为空");


    private int state;
    private String stateInfo;

    private PersonInfoStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    //遍历

    public PersonInfoStateEnum getOffsets(int state){
        for (PersonInfoStateEnum stateEnum : PersonInfoStateEnum.values()){
            if (state == stateEnum.getState()){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
