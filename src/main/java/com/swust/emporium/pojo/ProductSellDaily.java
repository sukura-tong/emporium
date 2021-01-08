package com.swust.emporium.pojo;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 顾客消费的商品映射
 */
public class ProductSellDaily {
    private Date createTime;//哪天的销量 精确到天
    private Integer total;//销量
    private Product product;//商品信息实体类
    private Shop shop;//店铺信息实体类
}
