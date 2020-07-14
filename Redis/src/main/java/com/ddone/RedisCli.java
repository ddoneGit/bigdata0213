package com.ddone;

import redis.clients.jedis.Jedis;

/**
 * @author ddone
 * @date 2020/7/13-18:02
 */
public class RedisCli {
    public static void main(String[] args) {
        //1.创建客户端
        Jedis jedis = new Jedis("hadoop102", 6379);
        //2.测试连接
        System.out.println(jedis.ping());
        /**
         * key操作
         */
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.get("num"));
        jedis.setnx("jjj", "uid");
        //3.关闭连接
        jedis.close();
    }
}
