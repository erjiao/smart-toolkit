package com.bmsoft.toolkit.web.config;

import com.bmsoft.toolkit.web.interceptor.ParamPageContextInterceptor;
import com.bmsoft.toolkit.web.properties.ParamPageContextInterceptorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author llk
 * @date 2022-06-17 14:47
 */
@Configuration
@EnableConfigurationProperties(ParamPageContextInterceptorProperties.class)
public class ParamPageContextInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    ParamPageContextInterceptorProperties paramPageContextInterceptorProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(paramPageContextInterceptor())
                .addPathPatterns(paramPageContextInterceptorProperties.getPathPatterns())
                .excludePathPatterns(paramPageContextInterceptorProperties.getExcludePathPatterns())
                .order(paramPageContextInterceptorProperties.getOrder());
    }

    @Bean
    public ParamPageContextInterceptor paramPageContextInterceptor() {
        return new ParamPageContextInterceptor();
    }
}
