package com.swust.emporium.dto;

import com.swust.emporium.enums.HeadLineStateEnum;
import com.swust.emporium.enums.ShopCategoryStateEnum;
import com.swust.emporium.pojo.ShopCategory;

import java.util.List;

public class ShopCategoryExecution {
    private int state;
    private String stateInfo;

    private int count;
    private ShopCategory shopCategory;
    private List<ShopCategory> shopCategoryList;


    public ShopCategoryExecution() {
    }

    public ShopCategoryExecution(ShopCategoryStateEnum shopCategoryStateEnum){
        this.state = shopCategoryStateEnum.getState();
        this.stateInfo = shopCategoryStateEnum.getStateInfo();
    }

    public ShopCategoryExecution(ShopCategoryStateEnum shopCategoryStateEnum, ShopCategory shopCategory){
        this.state = shopCategoryStateEnum.getState();
        this.stateInfo = shopCategoryStateEnum.getStateInfo();
        this.shopCategory = shopCategory;
    }

    public ShopCategoryExecution(ShopCategoryStateEnum shopCategoryStateEnum, List<ShopCategory> shopCategoryList){
        this.state = shopCategoryStateEnum.getState();
        this.stateInfo = shopCategoryStateEnum.getStateInfo();
        this.shopCategoryList = shopCategoryList;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public int getCount() {
        return count;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }
}
