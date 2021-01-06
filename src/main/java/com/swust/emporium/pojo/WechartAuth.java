package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 微信账号实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WechartAuth {
    private Long wechatAuthId;//微信账号ID
    private String openId;//openID
    private Date createTime;//创建时间
    private PersonInfo personInfo;//用户信息
}
