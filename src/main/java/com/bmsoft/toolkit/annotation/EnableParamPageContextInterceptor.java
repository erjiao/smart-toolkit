package com.bmsoft.toolkit.annotation;

import com.bmsoft.toolkit.config.ParamPageContextInterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用 ParamPageContextInterceptor
 *
 * @author llk
 * @date 2022-06-18 01:28
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({ParamPageContextInterceptorConfiguration.class})
public @interface EnableParamPageContextInterceptor {
}
