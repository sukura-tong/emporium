package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    private Long productCategoryId;//商品类别ID
    private Long shopId;//商品类别 商品ID
    private String productCategoryName;//商品类别名称
    private Integer priority;//权重
    private Date createTime;//创建时间
}
