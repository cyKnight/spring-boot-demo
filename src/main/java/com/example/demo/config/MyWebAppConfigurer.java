package com.example.demo.config;

import com.example.demo.interceptor.URLInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by user on 2018/6/2.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 把我们的拦截器注入为bean
     * @return
     */
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new URLInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/*");
//        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/*").excludePathPatterns("/a.do");
//        super.addInterceptors(registry);
    }

}
