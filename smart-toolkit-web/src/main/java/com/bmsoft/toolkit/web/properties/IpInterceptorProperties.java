package com.bmsoft.toolkit.web.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author llk
 * @date 2022-06-17 14:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "toolkit.interceptors.ip-interceptor")
public class IpInterceptorProperties extends InterceptorProperties {
}
