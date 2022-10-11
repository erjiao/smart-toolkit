package com.bmsoft.toolkit.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要翻译的实体类标识, 子类可继承
 * @author llk
 * @date 2019-11-10 21:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(value = ElementType.TYPE)
public @interface Translation {

    String value() default "";

}
