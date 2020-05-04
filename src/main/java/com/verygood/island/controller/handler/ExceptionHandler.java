package com.verygood.island.controller.handler;

import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description : 全局统一异常处理器
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-10-11
 */
@Aspect
@Component
@Log4j2
public class ExceptionHandler {


    @Around(value = "execution(public com.verygood.island.entity.dto.ResultBean com.verygood.island.controller..*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        return result;
    }

//    @Value("${alarm.email}")
//    private String[] email;


    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result;

        // 已知异常
        if (e instanceof BizException) {

            result = new ResultBean<>((BizException) e);
        } else {
            // 发生未知异常
            result = new ResultBean<>(e);
            e.printStackTrace();
            //发送异常邮箱通知
            //TODO 邮箱通知已关闭
            //InformationUtil.sendFreemarker(e);
//            log.info("邮箱通知已发送");
        }
        return result;
    }

}

