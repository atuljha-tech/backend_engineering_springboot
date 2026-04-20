package com.assignment.viralityengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long incrementBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setWithExpiry(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void pushToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public List<String> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void deleteList(String key) {
        redisTemplate.delete(key);
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Long getCounter(String key) {
        String value = get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

}
