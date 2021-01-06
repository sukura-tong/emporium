package com.swust.emporium.pojo;

import lombok.*;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 头条实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeadLine {
    private Long lineId;//头条ID
    private String lineName;//头条名称
    private String lineLink;//头条链接
    private String lineImg;//头条图像
    private Integer priority;//权重
    private Integer enableStatus;//状态
    private Date createTime;//创建时间
    private Date lastEditTime;//最后编辑时间
}
