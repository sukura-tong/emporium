package com.swust.emporium.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺授权
 */
@Data
public class ShopAuthMap {
    private Long shopAuthId;//主键id
    private String title;//职称名称
    private Integer titleFlag;//职称符号
    private Integer enableStatus;//授权有效状态
    private Date createTime;//创建时间
    private Date lastEditTime;//
    private PersonInfo employee;//员工信息
    private Shop shop;//店铺信息
}
