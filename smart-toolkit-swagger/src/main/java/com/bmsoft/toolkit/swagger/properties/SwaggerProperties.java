package com.bmsoft.toolkit.swagger.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author llk
 * @date 2022-06-28 18:16
 */
@Data
@ConfigurationProperties("smart-toolkit.swagger")
public class SwaggerProperties implements InitializingBean {

    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    private static final String BASE_PATH = "/**";

    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    /**
     * 组名
     */
    private String groupName = "default";

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePaths = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePaths = new ArrayList<>();

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 描述
     **/
    private String description = "";

    /**
     * 版本
     **/
    private String version = "";

    /**
     * 许可证
     **/
    private String license = "";

    /**
     * 许可证URL
     **/
    private String licenseUrl = "";

    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 联系人信息
     */
    private Contact contact = new Contact();


    private List<Header> headers = new ArrayList<>();


    private Boolean useDefault = true;


    @Data
    public static class Header {

        private String name;

        private String description = "";

        private String defaultValue =  "";

        private Boolean required = true;

    }


    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";

        /**
         * 联系人url
         **/
        private String url = "";

        /**
         * 联系人email
         **/
        private String email = "";

    }

    @Override
    public void afterPropertiesSet() {
        if (useDefault) {
            basePaths.add(BASE_PATH);
            excludePaths.addAll(0, DEFAULT_EXCLUDE_PATH);
        }
    }

}
