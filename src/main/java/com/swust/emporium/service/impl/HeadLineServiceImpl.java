package com.swust.emporium.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.cache.JedisUtil;
import com.swust.emporium.dao.HeadLineDao;
import com.swust.emporium.dto.HeadLineExecution;
import com.swust.emporium.enums.HeadLineStateEnum;
import com.swust.emporium.exceptions.HeadLineOperationException;
import com.swust.emporium.pojo.HeadLine;
import com.swust.emporium.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {


    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Strings strings;

    @Autowired
    private JedisUtil.Keys keys;


    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    /**
     * 根据条件查询相关信息
     * @param headLine
     * @return
     */
    @Override
    @Transactional
    public HeadLineExecution getHeadLineLists(HeadLine headLine) {
        HeadLineExecution hle = null;

        if (headLine == null){
            hle = new HeadLineExecution(HeadLineStateEnum.NULL_HEADLINE, headLine);
        }
        // select
        List<HeadLine> headLines = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        // 拼接处redis的key
        String key = HEAD_LINE_KEY;

        if (headLine != null && headLine.getEnableStatus() != null){
            key = key + "_"  + headLine.getEnableStatus();
        }

        if (!keys.exists(key)){
            // 从数据库查取数据 并缓存到redis中
            try {
                headLines = headLineDao.queryHeadLine(headLine);
            }catch (HeadLineOperationException e){
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new HeadLineOperationException("err ==> " + e.getMessage());
            }
            // 将数据缓存到redis内
            String jedisString = null;
            try {
                 jedisString = mapper.writeValueAsString(headLines);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            strings.set(key, jedisString);
        }else {
            String jedisDatas = strings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLines = mapper.readValue(jedisDatas, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new HeadLineOperationException("err ==> " + e.getMessage());
            }
        }

        hle = new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLines);
        return hle;
    }
}
