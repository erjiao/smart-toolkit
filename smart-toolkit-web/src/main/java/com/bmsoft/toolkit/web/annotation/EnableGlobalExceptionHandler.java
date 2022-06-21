package com.bmsoft.toolkit.web.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用全局异常处理器
 *
 * @see GlobalExceptionHandlerSelector
 *
 * @author llk
 * @date 2022-06-16 14:47
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({GlobalExceptionHandlerSelector.class})
public @interface EnableGlobalExceptionHandler {
}
