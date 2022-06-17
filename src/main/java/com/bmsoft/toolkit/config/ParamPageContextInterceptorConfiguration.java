package com.bmsoft.toolkit.config;

import com.bmsoft.toolkit.interceptor.ParamPageContextInterceptor;
import com.bmsoft.toolkit.properties.ParamPageContextInterceptorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    @ConfigurationProperties(prefix = "abc")
    public ParamPageContextInterceptor paramPageContextInterceptor() {
        return new ParamPageContextInterceptor();
    }
}
