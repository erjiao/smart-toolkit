package com.bmsoft.toolkit.config;

import com.bmsoft.toolkit.interceptor.IpInterceptor;
import com.bmsoft.toolkit.properties.IpInterceptorProperties;
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
@EnableConfigurationProperties(IpInterceptorProperties.class)
public class IpInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    IpInterceptorProperties ipInterceptorProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor())
                .addPathPatterns(ipInterceptorProperties.getPathPatterns())
                .excludePathPatterns(ipInterceptorProperties.getExcludePathPatterns())
                .order(ipInterceptorProperties.getOrder());
    }

    @Bean
    public IpInterceptor ipInterceptor() {
        return new IpInterceptor();
    }
}
