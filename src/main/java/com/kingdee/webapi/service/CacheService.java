package com.kingdee.webapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单缓存服务类
 * 用于模拟Redis缓存功能
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Service
public class CacheService {
    
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 添加缓存
     * 
     * @param key 缓存键
     * @param value 缓存值
     */
    public <T> void add(String key, T value) {
        try {
            if (value != null) {
                String jsonValue = objectMapper.writeValueAsString(value);
                cache.put(key, jsonValue);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("缓存序列化失败", e);
        }
    }
    
    /**
     * 获取缓存
     * 
     * @param key 缓存键
     * @return 缓存值
     */
    public String get(String key) {
        return cache.get(key);
    }
    
    /**
     * 删除缓存
     * 
     * @param key 缓存键
     */
    public void delete(String key) {
        cache.remove(key);
    }
    
    /**
     * 检查缓存是否存在
     * 
     * @param key 缓存键
     * @return 是否存在
     */
    public boolean exists(String key) {
        return cache.containsKey(key);
    }
    
    /**
     * 清空所有缓存
     */
    public void clear() {
        cache.clear();
    }
} 