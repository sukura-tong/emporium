package com.swust.emporium.service;

import com.swust.emporium.dto.HeadLineExecution;
import com.swust.emporium.pojo.HeadLine;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 头条业务逻辑层
 */
public interface HeadLineService {

    public final static String HEAD_LINE_KEY = "headlinelist";
    /**
     * 根据条件查询头条信息
     * @param headLine
     * @return
     */
    HeadLineExecution getHeadLineLists(HeadLine headLine);
}
