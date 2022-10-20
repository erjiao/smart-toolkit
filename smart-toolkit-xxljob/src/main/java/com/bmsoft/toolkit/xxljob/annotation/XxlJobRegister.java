package com.bmsoft.toolkit.xxljob.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlJobRegister {

    String cron();

    String jobDesc() default "register job";

    String author() default "register";

    /*
     * 默认为 ROUND 轮询方式
     * 可选： FIRST 第一个
     * */
    String executorRouteStrategy() default "ROUND";

    /**
     * 调度状态：0-停止，1-运行
     */
    int triggerStatus() default 0;
}
