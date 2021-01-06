package com.swust.emporium.dto;

import com.swust.emporium.enums.ShopStateEnum;
import com.swust.emporium.pojo.Shop;
import lombok.*;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 存储结果状态和状态标识
 *
 */
@Getter
@Setter
public class ShopExecution {
    //结果状态
    private int state;
    //状态描述
    private String stateInfo;
    //店铺数量
    private int count;
    //操作的Shop 增删改
    private Shop shop;
    //shop列表 查询
    private List<Shop> shopList;

    public ShopExecution(){}

    // 店铺操作失败的时候使用的构造器
    public ShopExecution(ShopStateEnum shopStateEnum){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }
    //  店铺操作成功的时候使用构的造器
    public ShopExecution(ShopStateEnum shopStateEnum, Shop shop){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }
    //  店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
    }
}
