package com.swust.emporium.service;

import com.swust.emporium.pojo.Area;

import java.util.List;

public interface AreaService {

    // 区域信息key
    public final static String AREA_LIST_KEY = "arealist";

    List<Area> getAreaList();
}

