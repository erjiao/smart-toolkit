package com.bmsoft.toolkit.web.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author llk
 * @date 2022-06-18 01:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "toolkit.interceptors.param-page-context-interceptor")
public class ParamPageContextInterceptorProperties extends InterceptorProperties {
}
