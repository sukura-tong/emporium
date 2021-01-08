package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 顾客店铺信息映射实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserShopMap {
    private Long userShopId;//主键ID
    private Date createTime;//创建时间
    private Integer point;//兑换消费积分
    private PersonInfo user;//兑换用户
    private Shop shop;//店铺信息
}
