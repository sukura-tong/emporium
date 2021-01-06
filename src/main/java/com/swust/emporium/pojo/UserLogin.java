package com.swust.emporium.pojo;

import lombok.*;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 用户登录实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLogin {
    private int uid;
    private String uname;
    private String upwd;
    private int status;// 默认0代表商店用户
}
