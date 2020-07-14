package com.ddone;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ddone
 * @date 2020/7/13-19:17
 */
public class JedisPoolTest {
    public static void main(String[] args) {
        //1.获取连接池
        JedisPool jedisPool = new JedisPool("hadoop102", 6379);
        //2.获取连接对象
        Jedis jedis = jedisPool.getResource();
        //3.执行具体操作
        System.out.println(jedis.ping());
        //4.释放连接
    }
}
