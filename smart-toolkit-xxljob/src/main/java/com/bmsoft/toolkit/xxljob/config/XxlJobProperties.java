package com.bmsoft.toolkit.xxljob.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author llk
 * @date 2022-10-20 21:46
 */
@Data
@ConfigurationProperties(prefix = "smart-toolkit.xxl.job")
public class XxlJobProperties implements InitializingBean, EnvironmentAware {

    private String accessToken;

    private Boolean enabled = true;

    private Admin admin = new Admin();

    private Executor executor = new Executor();

    @Data
    public static class Admin {
        private String addresses = "http://localhost:8080/xxl-job-admin";

        private String username = "admin";

        private String password = "123456";
    }

    @Data
    public static class Executor {

        private String appname;

        private String title;

        private String address;

        private String ip;

        private Integer port = 9999;

        private String logPath = "logs/xxl-job";

        private Integer logRetentionDays = 30;

    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Getter(AccessLevel.PRIVATE)
    private Environment environment;

    @Override
    public void afterPropertiesSet() {
        if (null == executor.getAppname() || "".equals(executor.getAppname())) {
            executor.setAppname(environment.getProperty("spring.application.name") + "-executor");
        }

        if (null == executor.getTitle() || "".equals(executor.getTitle())) {
            executor.setTitle(environment.getProperty("spring.application.name"));
        }
    }
}
