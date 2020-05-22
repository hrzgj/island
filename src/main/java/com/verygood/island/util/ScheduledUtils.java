package com.verygood.island.util;

import cn.hutool.json.JSONUtil;
import com.verygood.island.entity.Letter;
import com.verygood.island.entity.Notice;
import com.verygood.island.exception.bizException.BizException;
import com.verygood.island.service.LetterService;
import com.verygood.island.task.CapsuleSendingTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;

/**
 * @ClassName ScheduledUtils
 * @Description 定时发送工具类
 * @Author huange7
 * @Date 2020-05-21 20:20
 * @Version 1.0
 */
@Component
@Slf4j
public class ScheduledUtils{

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
        if (runnable == null){
            return;
        }
        Double score = redisUtils.score(KEY, runnable);
        if (score <= LocalDateTime.now().toEpochSecond(ZoneOffset.of(ZONE_OFFSET))){
            // 移除对应的score
            redisUtils.remove(KEY, runnable);
            // 时间到,可以执行定时任务
            ((Runnable)runnable).run();
        }
    }

    /**
     * 添加定时任务
     * @param startTime 开始执行时间
     * @param runnable 对应的runnable
     * @return boolean
     */
    public boolean addTask(LocalDateTime startTime, Runnable runnable){
        return redisUtils.add(KEY, runnable, startTime.toEpochSecond(ZoneOffset.of(ZONE_OFFSET)));
    }
}
