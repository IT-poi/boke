package com.cuit.boke.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class RedisTools {

    private RedisTemplate redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    public void removeKey(String key){
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    /**
     * 压栈,在队头压入数据
     * @param key list 的 key
     * @return
     */
    public Long lpush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Long lpushAll(String key, Object ... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 队列，入队,队尾追加
     * @param key
     * @param value
     */
    public Long rpush(String key, Object value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Long rpushAll(String key, Object ... values){
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 添加数据到set
     * @param key set的key
     * @param value 要添加的数据
     * @return 添加的条数
     */
    public Long sAdd(String key, Object ... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * set中 是否包含对应的value
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public <T> T get(String key, Class<T> cc) {
        return cc.cast(redisTemplate.opsForValue().get(key));
    }


    public List<String> getStringList(String key){
        return objectToString(redisTemplate.opsForList().range(key, 0, -1));
    }

    public List<Object> getList(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public<V> List<V> getList(String key, Class<V> cc){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public List getList(String key, long start, long stop){
        return redisTemplate.opsForList().range(key, start, stop);
    }

    public <V> List<V> getList(String key, Class<V> cc, long start, long stop){
        return redisTemplate.opsForList().range(key, start, stop);
    }

    private List<String> objectToString(List<Object> list) {
        List<String> stringList = new ArrayList<>();
        list.forEach(value -> stringList.add(value.toString()));
        return stringList;
    }

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
}