package com.bmsoft.toolkit.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: heyuhua
 * @Date: 2021/1/12 10:42
 */
@Data
@ConfigurationProperties(prefix = "smart-toolkit.minio")
public class MinioProperties {

    /**
     * endPoint是一个URL，域名，IPv4或者IPv6地址
     */
    private String endpoint;

//    /**
//     * 端口
//     */
//    private int port;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    private String secretKey;

//    /**
//     * 如果是true，则用的是https而不是http,默认值是true
//     */
//    private Boolean secure;

    /**
     * 默认存储桶
     */
    private String bucketName;


    private Boolean enabled = true;


    public String getUrl(String bucketName, String objectName) {
        return endpoint + "/" + bucketName + "/" + objectName;
    }

}
