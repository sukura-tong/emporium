package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 详情图片实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductImg {
    private Long productImgId;//图片ID
    private String imgAddr;//图片地址
    private String imgDesc;//图片描述 缩略图
    private Integer priority;//显示权重
    private Date createTime;//创建时间
    private Long productId;//产品ID  属于哪一类产品的ID
}
