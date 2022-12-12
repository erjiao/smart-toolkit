package com.bmsoft.toolkit.minio.config;

import com.bmsoft.toolkit.minio.ProxyType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: heyuhua
 * @Date: 2021/1/12 10:42
 */
@Data
@ConfigurationProperties(prefix = "smart-toolkit.minio")
public class MinioProperties {

    private static final String LOCAL_BASE_URI = "/api/st/file/download";

    /**
     * endPoint是一个URL，域名，IPv4或者IPv6地址
     *
     * 对象存储服务的URL
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


    /**
     * 访问时是否启动代理
     */
    private Boolean useProxy = false;


    private ProxyType proxyType = ProxyType.LOCAL;

    /**
     *  当 proxyType = ProxyType.CUSTOM 起作用
     *
     *  模式 { http[s]://ip:port/path }
     *
     *  生成访问的url时候会拼接 ?fullName={fullName}
     *
     */
    private String proxyEndpoint;

    private Boolean enabled = true;

    public String genUrl(String bucketName, String objectName) {
        if (useProxy) {
            if (proxyType == ProxyType.LOCAL) {
                return LOCAL_BASE_URI + "?fullName=" + objectName;
            }
            if (proxyType == ProxyType.CUSTOM) {
                return this.proxyEndpoint + "?fullName=" + objectName;
            }
        }
        return endpoint + "/" + bucketName + "/" + objectName;
    }

    public String wipeUrl(String url) {
        if (useProxy) {
            if (proxyType == ProxyType.LOCAL) {
                return url.replace(LOCAL_BASE_URI + "?fullName=", "");
            }
            if (proxyType == ProxyType.CUSTOM) {
                return url.replace(this.proxyEndpoint + "?fullName=", "");
            }
        }

        return url.replace(endpoint + "/" + bucketName + "/", "");
    }

}
