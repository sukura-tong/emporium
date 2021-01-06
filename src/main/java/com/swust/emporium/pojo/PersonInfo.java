package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 用户信息实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonInfo {

    private Long userId;//用户ID
    private String name;//用户清楚
    private String profileImg;//头像
    private String email;//电子邮件
    private String gender;//性别
    private Integer enableStatus;//状态
    // 0 顾客 1 店家 2 超级管理员
    private Integer userType;//用户类别
    private Date createTime;//创建时间
    private Date lastEditTime;//最后编辑时间
}
