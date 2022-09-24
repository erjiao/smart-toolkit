package com.bmsoft.toolkit.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.bmsoft.toolkit.mybatis.support.BaseEntityMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author llk
 * @date 2022-07-06 19:16
 */
@Configuration
@AutoConfigureAfter(SysStDictConfiguration.class)
public class MybatisPlusConfiguration {

    /**
     * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    /**
     * {@code BaseEntity} 实体字段自动填充
     * @return {@link MetaObjectHandler}
     */
    @Bean
    @ConditionalOnMissingBean(BaseEntityMetaObjectHandler.class)
    public BaseEntityMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new BaseEntityMetaObjectHandler();
    }

}
