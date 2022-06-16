package com.bmsoft.toolkit;

import com.bmsoft.toolkit.interceptor.IpInterceptor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author llk
 * @date 2020-08-17 23:13
 */
@Data
@ConfigurationProperties(prefix = "toolkit.interceptors.ip-interceptor.access-control")
public class AccessCtrlProperties implements IpInterceptor.AccessCtrl {

    private boolean enable;

    private Set<String> whiteIps = new HashSet<>();

    private Set<String> blackIps = new HashSet<>();
}
