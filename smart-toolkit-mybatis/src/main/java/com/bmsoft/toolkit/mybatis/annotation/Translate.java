package com.bmsoft.toolkit.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段翻译类
 * @author llk
 * @date 2019-11-10 21:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = ElementType.FIELD)
public @interface Translate {

    String value() default "";

    /**
     * 字典类型
     */
    String type();

    /**
     * 根据哪个字段翻译
     */
    String byField();
}
