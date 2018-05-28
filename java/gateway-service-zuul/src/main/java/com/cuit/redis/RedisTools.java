package com.cuit.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;
import java.util.function.Function;

@Component
@SuppressWarnings("unchecked")
public class RedisTools {

    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        jedisPool = new JedisPool(config, "193.112.112.136", 6379, 2000, "yinjk12345");
    }

    /**
     * set中 是否包含对应的value
     * @param key
     * @param value
     * @return
     */
    public static boolean siscontains(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, String.valueOf(value));
        }finally {
            jedis.close();
        }
    }

    public static <R> R execute(Function<Jedis, R> function) {
        Jedis jedis = jedisPool.getResource();
        R result = function.apply(jedis);
        jedis.close();
        return result;
    }

    public static void main(String[] args){
        System.out.println(siscontains("logoutTokens", "hello.word.token"));
        Set<String> result = RedisTools.execute(jedis -> jedis.smembers("token"));
        result.forEach(System.out::println);
    }
}