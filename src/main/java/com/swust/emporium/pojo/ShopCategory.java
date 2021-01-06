package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺类别实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategory {
    private Long shopCategoryId;//店铺ID
    private String shopCategoryName;//店铺名称
    private String shopCategoryDesc;//店铺描述
    private String shopCategoryImg;//店铺图像
    private Integer priority;//权重
    private Date createTime;//创建时间
    private Date lastEditTime;//最后编辑时间
    private ShopCategory parent;//上级ID
    // eg: 饮料 --> 矿泉水 --> 农夫山泉
}
