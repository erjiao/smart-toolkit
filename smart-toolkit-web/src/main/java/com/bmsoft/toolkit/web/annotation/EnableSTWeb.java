package com.bmsoft.toolkit.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-06-28 10:37
 */
@EnableIpInterceptor
@EnableLogAspect
@EnableGlobalExceptionHandler
@EnableParamPageContextInterceptor
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
public @interface EnableSTWeb {
}
