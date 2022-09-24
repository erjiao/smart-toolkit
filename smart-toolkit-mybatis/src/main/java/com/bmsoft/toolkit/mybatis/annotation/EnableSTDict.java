package com.bmsoft.toolkit.mybatis.annotation;

import com.bmsoft.toolkit.mybatis.config.SysStDictConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-09-24 01:43
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({SysStDictConfiguration.class})
public @interface EnableSTDict {
}
