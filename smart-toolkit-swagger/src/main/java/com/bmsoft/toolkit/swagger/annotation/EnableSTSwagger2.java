package com.bmsoft.toolkit.swagger.annotation;

import com.bmsoft.toolkit.swagger.config.SwaggerConfiguration;
import com.bmsoft.toolkit.swagger.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author llk
 * @date 2022-06-28 18:14
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerConfiguration.class})
public @interface EnableSTSwagger2 {
}
