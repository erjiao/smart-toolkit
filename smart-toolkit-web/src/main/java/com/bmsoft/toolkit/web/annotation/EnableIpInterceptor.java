package com.bmsoft.toolkit.web.annotation;

import com.bmsoft.toolkit.web.config.IpInterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用 ipInterceptor
 *
 * @author llk
 * @date 2022-06-17 16:21
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({AccessCtrlDeferredSelector.class, IpInterceptorConfiguration.class})
public @interface EnableIpInterceptor {
}
