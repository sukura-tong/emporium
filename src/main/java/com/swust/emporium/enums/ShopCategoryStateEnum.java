package com.swust.emporium.enums;

public enum ShopCategoryStateEnum {

    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_HEADLINE_ID(-1002,"头条ID为空"),
    NULL_HEADLINE(-1003,"产品信息为空");

    private int state;
    private String stateInfo;

    private ShopCategoryStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopCategoryStateEnum getIndexOf(int state){
        for (ShopCategoryStateEnum pse : ShopCategoryStateEnum.values()){
            if (pse.getState() == state){
                return pse;
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
