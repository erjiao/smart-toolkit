package com.bmsoft.toolkit.core.annotation;

import com.bmsoft.toolkit.core.config.TransactionAdviceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用事务环绕通知
 *
 * 需要配置 {@code toolkit.transaction-advice.pointcut-expression}
 *
 * @see TransactionAdviceConfiguration
 *
 * @author llk
 * @date 2022-06-16 16:04
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import(TransactionAdviceConfiguration.class)
public @interface EnableTransactionAdviceConfiguration {
}
