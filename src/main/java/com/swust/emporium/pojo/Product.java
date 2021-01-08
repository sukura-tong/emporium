package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;//产品ID
    private String productName;//产品名称
    private String productDesc;//产品描述
    private String imgAddr;// 简略图
    private String normalPrice;//产品原价
    private String promotionPrice;//产品折扣价格
    private Integer point;//商品积分
    private Integer priority;//产品权重
    private Date createTime;//产品上架时间
    private Date lastEditTime;//编辑时间
    // 0 下架
    // 1 在前端系统进行展示
    private Integer enableStatus;//产品状态
    private List<ProductImg> productImgList;//商品详情图片列表
    private ProductCategory productCategory;//产品类别 类别ID
    private Shop shop;//店铺 店铺ID
}
