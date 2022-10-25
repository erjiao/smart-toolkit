package com.bmsoft.toolkit.core.config;

import com.bmsoft.toolkit.core.holder.SpringContextHolder;
import com.bmsoft.toolkit.core.utils.RedisUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author llk
 * @date 2022-10-25 14:18
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CoreConfiguration {

    @Bean("springContextHolder")
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnMissingBean
    public RedisUtils redisUtils(StringRedisTemplate stringRedisTemplate) {
        return new RedisUtils(stringRedisTemplate);
    }

}
