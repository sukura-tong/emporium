package com.swust.emporium.util.wechat.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAccessToken {

    @JsonProperty("access_token") //将数据转为json格式
    private String accessToken;//获取到的凭证

    @JsonProperty("expires_in")
    private String expiresIn;//凭证有效时间

    @JsonProperty("refresh_token")
    private String refreshToken;//更新令牌

    @JsonProperty("openid")
    private String openId;//用户在公众号下的身份标识，是对此微信号的唯一标识

    @JsonProperty("scope")
    private String scope;//权限范围
}
