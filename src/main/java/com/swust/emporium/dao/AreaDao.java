package com.swust.emporium.dao;

import com.swust.emporium.pojo.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 列出区域列表
     * @return
     */
    List<Area> queryArea();
}
