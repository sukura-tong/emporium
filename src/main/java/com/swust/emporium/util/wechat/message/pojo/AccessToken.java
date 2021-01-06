package com.swust.emporium.util.wechat.message.pojo;

import lombok.*;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 微信通用接口凭证
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessToken {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;
}
