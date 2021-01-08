package com.swust.emporium.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 顾客已经领取的奖品映射
 */
@Data
public class UserAwardId {
    private Long userAwardId;//主键ID
    private Date createTime;//创建时间
    private Integer userStatus;//奖品是否兑换 0未兑换 1已兑换
    private Integer point;//兑换消耗积分
    private PersonInfo user;//顾客信息实体类
    private Award award;//奖品信息实体类
    private Shop shop;//店铺信息实体类
    private PersonInfo operator;//操作员实体类
}
