package com.swust.emporium.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * redis连接对象
 */
public class JedisPoolWriper {

    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String hostName, final int port){
        try {
            jedisPool = new JedisPool(poolConfig, hostName, port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取redis连接池对象
     */
    public JedisPool getJedisPool(){
        return jedisPool;
    }

    /***
     * 注入redis连接池对象
     */
    public void setJedisPool(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }
}
