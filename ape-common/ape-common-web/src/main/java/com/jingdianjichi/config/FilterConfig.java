package com.jingdianjichi.config;

import com.jingdianjichi.trace.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Filter过滤器配置
 *
 * @author: ChickenWing
 * @date: 2023/1/26
 */
@Configuration
public class FilterConfig {

    @Resource
    private TraceIdFilter traceIdFilter;

    @Bean
    public FilterRegistrationBean registerTraceFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(traceIdFilter);
        registration.addUrlPatterns("/*");
        registration.setName("traceIdFilter");
        registration.setOrder(1);
        return registration;
    }

}
