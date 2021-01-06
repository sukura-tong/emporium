package com.swust.emporium.enums;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别枚举类
 */
public enum ProductCategoryStateEnum {

    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOPID(-1002,"shop为空");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /***
     * 根据传入的状态值返回状态
     * @param state
     * @return
     */
    public static ProductCategoryStateEnum stateOfIndex(int state){
        for (ProductCategoryStateEnum productCategoryStateEnum : ProductCategoryStateEnum.values()){
            if (productCategoryStateEnum.getState() == state){
                return productCategoryStateEnum;
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
