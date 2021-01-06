package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 区域实体类
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private Integer areaId;//区域ID
    private String areaName;//区域名称
    private Integer priority;// 权重
    private Date createTime;//区域创建时间
    private Date lastEditTime;//区域最后编辑时间
}
