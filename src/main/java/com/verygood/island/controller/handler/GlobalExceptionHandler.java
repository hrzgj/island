package com.verygood.island.controller.handler;

import com.verygood.island.entity.dto.ResultBean;
import com.verygood.island.exception.bizException.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @description 全局异常处理器
 * @date 2019-08-12 19:19
 */
@Slf4j
@RestControllerAdvice
@CrossOrigin
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.info("请求异常" + e.getMessage());
        e.printStackTrace();
        return null;
    }

    @ExceptionHandler(com.verygood.island.exception.bizException.BizException.class)
    public ResultBean<?> bizException(BizException e) {
        return new ResultBean<>(e);
    }

    @ExceptionHandler(org.apache.shiro.authc.AuthenticationException.class)
    public ResultBean<?> authenticationException(Exception e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException(e.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResultBean<?> nullPointerException(NullPointerException e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("错误!参数不匹配"));
    }

    @ExceptionHandler({com.alibaba.druid.pool.GetConnectionTimeoutException.class})
    public ResultBean<?> dataBaseException(Exception e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("服务器访问人数过多，请稍后重试"));
    }


    @ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
    public ResultBean<?> maxUploadexception(org.springframework.web.multipart.MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("您上传的文件大小超过限制"));
    }

    @ExceptionHandler(Throwable.class)
    public ResultBean<?> unknownException(Throwable e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("发生了未知的异常，请告知程序员哥哥前来修复"));
    }

    @ExceptionHandler(
            {org.springframework.http.converter.HttpMessageNotReadableException.class,
                    org.springframework.web.bind.MissingServletRequestParameterException.class,
                    org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    public ResultBean<?> http400Handler(Exception e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("您的请求错误，缺少请求体或格式错误"));
    }

    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResultBean<?> http405Handler(org.springframework.web.HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("服务器并不支持您所使用的请求方法"));
    }

    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    public ResultBean<?> http405Handler(org.springframework.web.HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("您的请求体格式不正确"));
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResultBean<?> http500Handler(IllegalStateException e) {
        e.printStackTrace();
        return new ResultBean<>(new BizException("您的请求体格式不正确"));
    }

}
