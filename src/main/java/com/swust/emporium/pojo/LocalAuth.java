package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 本地账号实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocalAuth {
    private Long localAuthId;//本地账号ID
    private String username;//用户名
    private String password;//密码
    private Date createTime;//创建时间
    private Date lastEditTime;//最后编辑时间
    private PersonInfo personInfo;//用户信息
}
