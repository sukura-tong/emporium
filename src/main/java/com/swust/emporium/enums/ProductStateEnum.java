package com.swust.emporium.enums;

import com.swust.emporium.pojo.Product;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 产品操作枚举类
 */
public enum ProductStateEnum {

    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_PRODUCT_ID(-1002,"产品ID为空"),
    NULL_PRODUCT(-1003,"产品信息为空");

    private int state;
    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum getIndexOf(int state){
        for (ProductStateEnum pse : ProductStateEnum.values()){
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
