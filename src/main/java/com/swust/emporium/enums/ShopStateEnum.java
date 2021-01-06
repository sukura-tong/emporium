package com.swust.emporium.enums;


/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺操作的枚举类
 * 用来存储店铺相关操作信息的描述内容
 */
public enum ShopStateEnum {

    CHECK(0,"审核中"),
    OFFLINE(-1,"非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOPID(-1002,"shop为空"),
    NULL_SHOP(-1003,"shop信息为空");

    private int state;
    private String stateInfo;

    /**
     * 枚举构造方法
     * @param state
     * @param stateInfo
     */
    private ShopStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /***
     * 根据传入的state值返回对应的数据
     * @param state
     * @return
     */
    public static ShopStateEnum stateEnum(int state){
        for (ShopStateEnum stateEnum : ShopStateEnum.values()){
            if (stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }


    public String getStateInfo(){
        return this.stateInfo;
    }

    public int getState(){
        return this.state;
    }
}
