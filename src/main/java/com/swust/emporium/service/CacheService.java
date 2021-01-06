package com.swust.emporium.service;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * jedis
 * 操作，业务逻辑层
 */
public interface CacheService {

    /**
     * 根据传入的key前缀删除缓存与redis中的所有数据
     * @param keyPrefix
     */
    void removeFromRedisCache(String keyPrefix);
}
