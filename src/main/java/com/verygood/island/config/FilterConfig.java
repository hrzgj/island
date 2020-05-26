package com.verygood.island.config;


import com.verygood.island.controller.filter.ExceptionHandlerFilter;
import com.verygood.island.controller.filter.ParamsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @description 过滤器配置
 * @date 2020-05-22 20:28
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ParamsFilter> paramsFilterRegistration() {
        FilterRegistrationBean<ParamsFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ParamsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ParamsFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }


    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterRegistration() {
        FilterRegistrationBean<ExceptionHandlerFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ExceptionHandlerFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ExceptionHandlerFilter");
        registration.setOrder(1);
        return registration;
    }
}
