package com.swust.emporium.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 奖品信息实体类
 */
@Data
public class Award {
    private Long awardId;//主键ID
    private String awardName;//奖品名称
    private String awardDesc;//奖品描述
    private String awardImg;//图片地址
    private Integer point;//积分
    private Date createTime;//创建时间
    private Date lastEditTime;
    private Integer enableStatus;//可用状态 0不可用 1可用
    private Long shopId;//属于哪个店铺
}
