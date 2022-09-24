package com.bmsoft.toolkit.mybatis.config;

import com.bmsoft.toolkit.mybatis.support.SysStDictService;
import com.bmsoft.toolkit.mybatis.support.TranslationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author llk
 * @date 2022-09-24 01:36
 */
@Configuration
@MapperScan("com.bmsoft.toolkit.mybatis.dao")
public class SysStDictConfiguration {


    @Bean(value = "sysStDictService", initMethod = "initDictHolder")
    public SysStDictService sysStDictService() {
        return new SysStDictService();
    }

    /**
     * 翻译拦截器
     *
     * @return
     */
    @Bean
    public TranslationInterceptor translationInterceptor() {
        return new TranslationInterceptor();
    }

}
