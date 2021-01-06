package com.swust.emporium.service.impl;

import com.swust.emporium.cache.JedisUtil;
import com.swust.emporium.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromRedisCache(String keyPrefix) {
        Set<String> keys =
                jedisKeys.keys(keyPrefix + "*");

        for (String key : keys){
            jedisKeys.del(key);
        }
    }
}
