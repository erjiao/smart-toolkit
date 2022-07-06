package com.bmsoft.toolkit.web.config;

import com.bmsoft.toolkit.web.properties.AccessCtrl;
import com.bmsoft.toolkit.web.properties.AccessCtrlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author llk
 * @date 2022-06-18 01:23
 */
@Configuration
public class AccessCtrlConfiguration {

    @Bean
    @ConditionalOnMissingBean(AccessCtrl.class)
    @ConfigurationProperties(prefix = "smart-toolkit.interceptors.ip-interceptor.access-control")
    public AccessCtrl accessCtrl() {
        return new AccessCtrlProperties();
    }

}
