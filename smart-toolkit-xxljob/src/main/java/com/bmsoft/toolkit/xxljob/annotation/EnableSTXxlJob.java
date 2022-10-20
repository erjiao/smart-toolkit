package com.bmsoft.toolkit.xxljob.annotation;

import com.bmsoft.toolkit.xxljob.config.XxlJobConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-10-20 22:07
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({XxlJobConfiguration.class})
public @interface EnableSTXxlJob {

}
