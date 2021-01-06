package com.swust.emporium.dao;

import com.swust.emporium.pojo.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 头条操作数据库 Dao层
 */
public interface HeadLineDao {

    /**
     * 根据信息查询数据
     * @param headLine
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLine);
}
