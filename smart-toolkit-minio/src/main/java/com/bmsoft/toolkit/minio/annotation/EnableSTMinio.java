package com.bmsoft.toolkit.minio.annotation;

import com.bmsoft.toolkit.minio.config.MinioConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-10-19 15:32
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Import({MinioConfiguration.class})
public @interface EnableSTMinio {
}
