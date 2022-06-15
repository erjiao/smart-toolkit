package com.bmsoft.toolkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注对应的类需要被spring管理才能使用
 *
 * @author llk
 * @date 2019-10-10 01:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface UseBySpringBean {

    String value() default "";
}
