package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private Long shopId;//店铺ID
    private String shopName;//店铺名称
    private String shopDesc;//店铺描述
    private String shopAddr;//地址
    private String phone;//联系方式
    private String shopImg;//店铺图片 门面照
    private Integer priority;//权重
    private Date createTime;//创建时间
    private Date lastEditTime;//最后编辑时间
    private Integer enableStatus;//店铺状态
    private String advice;
    private Area area;//区域信息 -- 区域ID
    private PersonInfo owner;//用户信息 -- 用户ID
    private ShopCategory shopCategory;//产品信息 --商品ID

}
