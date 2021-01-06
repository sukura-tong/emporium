package com.swust.emporium.util.wechat.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 微信用户实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WechartUser implements Serializable {

    private static final long serialVersionUID = -4889402573106260602L;

    @JsonProperty("openid")
    private String openId;//用户ID

    @JsonProperty("nickname")
    private String nickName;//昵称

    @JsonProperty("sex")
    private int sex;//性别

    @JsonProperty("province")
    private String province;//省份

    @JsonProperty("city")
    private String city;//城市

    @JsonProperty("county")
    private String country;//区

    @JsonProperty("headimgurl")
    private String headimgurl;//头像

    @JsonProperty("language")
    private String language;//语言

    @JsonProperty("privilege")
    private String[] privilege;//权限

}
