package com.swust.emporium.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.cache.JedisUtil;
import com.swust.emporium.dao.AreaDao;
import com.swust.emporium.pojo.Area;
import com.swust.emporium.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    // 获取redis操作对象
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    /***
     * 查询区域信息
     * @return
     */
    @Override
    @Transactional
    public List<Area> getAreaList() {
        String key = AREA_LIST_KEY;
        List<Area> areas = null;

        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)){
            // 从mysql数据库获取对应数据 并存储于redis缓存数据库内
            areas = areaDao.queryArea();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(areas);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new RuntimeException("err ==> " + e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        }else {
            String areaStrings = jedisStrings.get(key);
            try {
                // 构建类型转换工厂类
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
                areas = mapper.readValue(areaStrings, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new RuntimeException("err ==> " + e.getMessage());
            }
        }
        return areas;
    }

}
