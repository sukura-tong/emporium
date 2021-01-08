package com.swust.emporium.pojo;

import lombok.Data;

import java.time.Period;
import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 顾客消费的商品映射
 */
@Data
public class UserProductMap {
    private Long userProductId;//主键ID
    private Date createTime;//创建时间
    private Integer point;//消费商品所获得的积分
    private PersonInfo user;//顾客信息实体类
    private Product product;//商品信息实体类
    private Shop shop;//店铺信息实体类
    private PersonInfo personInfo;//操作员信息实体类
}
