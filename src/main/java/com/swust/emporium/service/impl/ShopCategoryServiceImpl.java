package com.swust.emporium.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.cache.JedisUtil;
import com.swust.emporium.dao.ShopCategoryDao;
import com.swust.emporium.dto.ShopCategoryExecution;
import com.swust.emporium.enums.ShopCategoryStateEnum;
import com.swust.emporium.exceptions.ShopCategoryOpertionException;
import com.swust.emporium.pojo.ShopCategory;
import com.swust.emporium.service.ShopCategoryService;
import com.swust.emporium.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别业务逻辑层
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Strings strings;

    @Autowired
    private JedisUtil.Keys keys;


    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Override
    @Transactional
    public ShopCategoryExecution getShopCategoryList(ShopCategory shopCategoryCondition) {

        String key = SHOP_CATEGORY_KEY;
        // 依据传入的条件进行key的拼接

        if (shopCategoryCondition == null){
            key = key + "_allfirstlevel";
        }else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null && shopCategoryCondition.getParent().getShopCategoryId() != null){
            //若parentId为飞空，则列出该parentId下的所有子类别
            key  = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        }else if (shopCategoryCondition != null){
            // 列出所有子类别
            key = key + "_allsecondlevel";
        }

        ObjectMapper mapper = new ObjectMapper();
        List<ShopCategory> shopCategories = null;

        if (!keys.exists(key)){
            try {
                shopCategories = shopCategoryDao.queryShopCategory(shopCategoryCondition);
                for (ShopCategory shopCategory : shopCategories){
                    String shopCategoryImg = shopCategory.getShopCategoryImg();
                    // 发送到前端则需要处理路径
                    String replace = PathUtils.replaceMaoHao(shopCategoryImg);
                    shopCategory.setShopCategoryImg(replace);
                }
            }catch (ShopCategoryOpertionException e){
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new ShopCategoryOpertionException("err ==> " + e.getMessage());
            }
            // 缓存
            String jedisJson = null;
            try {
                jedisJson = mapper.writeValueAsString(shopCategories);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("err ==> " + e.getMessage());
                throw new ShopCategoryOpertionException("err ==> " + e.getMessage());
            }
            strings.set(key, jedisJson);
        }else {
            String jedisDatas = strings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);

            try {
                shopCategories = mapper.readValue(jedisDatas, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ShopCategoryExecution sce = new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategories);
        return sce;
    }
}
