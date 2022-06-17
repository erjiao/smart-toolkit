package com.bmsoft.toolkit.config;

import com.bmsoft.toolkit.properties.AccessCtrl;
import com.bmsoft.toolkit.properties.AccessCtrlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author llk
 * @date 2022-06-18 01:23
 */
@Configuration
@ConditionalOnMissingBean(AccessCtrl.class)
public class AccessCtrlConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "toolkit.interceptors.ip-interceptor.access-control")
    public AccessCtrl accessCtrl() {
        return new AccessCtrlProperties();
    }

}
