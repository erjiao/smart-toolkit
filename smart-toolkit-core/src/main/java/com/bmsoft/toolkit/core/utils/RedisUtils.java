package com.bmsoft.toolkit.core.utils;

import com.bmsoft.toolkit.core.annotation.UseBySpringBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author llk
 * @date 2019-10-09 13:58
 */
@UseBySpringBean
public class RedisUtils {

    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;
    private final HashOperations<String, String, String> hashOperations;
    private final ListOperations<String, String> listOperations;
    private final SetOperations<String, String> setOperations;
    private final ZSetOperations<String, String> zSetOperations;

    public RedisUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        zSetOperations = redisTemplate.opsForZSet();
    }
    // ------------------- key operations begin ---------------------
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    // ------------------- key operations end ---------------------


    // ------------------- string operations begin ---------------------
    public void set(String key, String value) {
        valueOperations.set(key, value);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        valueOperations.set(key, value, timeout, timeUnit);
    }

    public void set(String key, String value, long seconds) {
        this.set(key, value, seconds, TimeUnit.SECONDS);
    }

    public Boolean setIfAbsent(String key, String value) {
        return valueOperations.setIfAbsent(key, value);
    }

    public String get(String key) {
        return valueOperations.get(key);
    }

    public Long increment(String key) {
        return valueOperations.increment(key);
    }
    // ------------------ string operations end ------------------------

    // ------------------ list operations begin ------------------------
    public void leftPush(String key, String value){
        listOperations.leftPush(key, value);
    }

    public void rightPush(String key, String value){
        listOperations.rightPush(key, value);
    }

    public void leftPushAll(String key, List<String> list ) {
        listOperations.leftPushAll(key, list);
    }

    public void rightPushAll(String key, List<String> list ) {
        listOperations.rightPushAll(key, list);
    }

    public List<String> lGetAll(String key) {
        return lGet(key, 0, -1);
    }

    public List<String> lGet(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    // ------------------ list operations end ------------------------

    // ------------------- set operations begin ---------------------
    public void add(String key, Collection<String> collection) {
        this.add(key, collection.toArray(new String[0]));
    }

    public void add(String key, String ... values) {
        setOperations.add(key, values);
    }

    public void sRemove(String key, String ... values) {
        setOperations.remove(key, values);
    }


    public Set<String> members(String key) {
        return setOperations.members(key);
    }

    public boolean isMember(String key, String value) {
        return setOperations.isMember(key, value);
    }

    // ------------------- set operations end ---------------------


    // ------------------- hash operations begin ---------------------
    public String hGet(String key, String field) {
        return hashOperations.get(key, field);
    }
    public Map<String, String> hGetAll(String key) {
        return hashOperations.entries(key);
    }
    public List<String> hMultiGet(String key, Collection<String> fields) {
        return hashOperations.multiGet(key, fields);
    }

    public void hPut(String key, String hashKey, String value) {
        hashOperations.put(key, hashKey, value);
    }

    public void hPutAll(String key, Map<String, String> maps) {
        hashOperations.putAll(key, maps);
    }

    public Boolean hPutIfAbsent(String key, String hashKey, String value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    public Long hDelete(String key, Object... fields) {
        return hashOperations.delete(key, fields);
    }

    public boolean hExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    public Set<String> hKeys(String key) {
        return hashOperations.keys(key);
    }

    public Long hSize(String key) {
        return hashOperations.size(key);
    }

    public List<String> hValues(String key) {
        return hashOperations.values(key);
    }
    // ------------------- hash operations end ---------------------

    // ------------------- publish operations start -------------------
    public void publish(String channel, String msg) {
        redisTemplate.convertAndSend(channel,msg);
    }

    // ------------------- publish operations end ---------------------

}
