package com.bmsoft.toolkit.log.annotation;

import com.bmsoft.toolkit.log.config.LogAspectConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用日志切面
 *
 * 需要配置 {@code toolkit.log-handle.controller-pointcut-expression}
 *
 * @see LogAspectConfiguration
 *
 * @author llk
 * @date 6/15/22 11:42 PM
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import(LogAspectConfiguration.class)
public @interface EnableLogAspect {
}
