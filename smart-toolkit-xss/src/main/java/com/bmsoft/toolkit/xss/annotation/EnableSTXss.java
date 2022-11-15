package com.bmsoft.toolkit.xss.annotation;

import com.bmsoft.toolkit.xss.XssAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-11-15 09:46
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import(XssAutoConfiguration.class)
public @interface EnableSTXss {
}
