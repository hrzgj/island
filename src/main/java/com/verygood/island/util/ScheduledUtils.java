package com.verygood.island.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @ClassName ScheduledUtils
 * @Description 定时发送工具类
 * @Author huange7
 * @Date 2020-05-21 20:20
 * @Version 1.0
 */
@Component
@Slf4j
public class ScheduledUtils {

    @Autowired
    private RedisUtils redisUtils;

    private static final String KEY = "letters";

    private static final String ZONE_OFFSET = "+8";

    /**
     * 100ms执行一次
     */
    @Scheduled(fixedDelay = 100)
    public void checkAndSend() {
        Object runnable = redisUtils.range(KEY, 0, 0);
        if (runnable == null) {
            return;
        }
        Double score = redisUtils.score(KEY, runnable);
        if (score <= LocalDateTime.now().toEpochSecond(ZoneOffset.of(ZONE_OFFSET))) {
            // 移除对应的score
            redisUtils.remove(KEY, runnable);
            // 时间到,可以执行定时任务
            ((Runnable) runnable).run();
        }
    }

    /**
     * 添加定时任务
     *
     * @param startTime 开始执行时间
     * @param runnable  对应的runnable
     * @return boolean
     */
    public boolean addTask(LocalDateTime startTime, Runnable runnable) {

        Boolean result;
        ZoneOffset of = ZoneOffset.of(ZONE_OFFSET);
        long l = startTime.toEpochSecond(of);
        result = redisUtils.add(KEY, runnable, l);
        if (result == null){
            return false;
        }
        return result;
    }
}
