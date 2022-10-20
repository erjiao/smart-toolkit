package com.bmsoft.toolkit.xxljob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author llk
 * @date 2022-10-20 21:46
 */
@Data
@ConfigurationProperties(prefix = "smart-toolkit.xxl.job")
public class XxlJobProperties {

    private String accessToken;

    private Admin admin;

    private Executor executor;

    @Data
    public static class Admin {
        private String addresses;

        private String username;

        private String password;
    }

    @Data
    public static class Executor {

        private String appname;

        private String title;

        private String address;

        private String ip;

        private Integer port;

        private String logPath;

        private Integer logRetentionDays;


    }
}
