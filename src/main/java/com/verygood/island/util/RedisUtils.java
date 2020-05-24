package com.verygood.island.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @ClassName RedisUtil
 * @Description redis工具类
 * @Author huange7
 * @Date 2020-05-21 11:06
 * @Version 1.0
 */
@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 往定时列表中增加定时任务
     *
     * @param key   定时任务列表名称
     * @param value 定时任务值
     * @param score 定时任务启动时间
     * @return boolean 是否设置成功
     */
    public boolean add(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }


    /**
     * 往redis中放置数据
     *
     * @param key   键
     * @param value 值
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-23
     */
    public boolean setForever(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * 移除数据
     *
     * @param key 键
     */
    public void remove(String key) {
        redisTemplate.opsForZSet().remove(key);
    }

    /**
     * 删除定时任务列表中的任务
     *
     * @param key   定时任务列表名称
     * @param value 定时任务值
     */
    public void remove(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取一定范围的任务列表
     *
     * @param key   定时任务列表名称
     * @param start 开始索引
     * @param end   结束索引
     * @return Set<Object> 结果集
     */
    public Object range(String key, long start, long end) {
        Set<Object> objects = redisTemplate.opsForZSet().range(key, start, end);
        if (objects == null) {
            return null;
        }
        if (objects.iterator().hasNext()) {
            return objects.iterator().next();
        }
        return null;
    }

    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }


}
