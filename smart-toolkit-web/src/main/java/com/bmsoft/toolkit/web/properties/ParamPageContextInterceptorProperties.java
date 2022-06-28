package com.bmsoft.toolkit.web.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author llk
 * @date 2022-06-18 01:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "toolkit.interceptors.param-page-context-interceptor")
public class ParamPageContextInterceptorProperties extends InterceptorProperties implements InitializingBean {

    private static final List<String> DEFAULT_PATH_PATTERNS =
            Collections.unmodifiableList(Arrays.asList("/**"));

    private static final List<String> DEFAULT_EXCLUDE_PATH_PATTERNS =
            Collections.unmodifiableList(Arrays.asList("/error"));


    private List<String> pathPatterns = new ArrayList<>();

    private List<String> excludePathPatterns = new ArrayList<>();

    private Integer order = 0;

    private boolean addDefaultPatterns = true;

    @Override
    public void afterPropertiesSet() {
        if (addDefaultPatterns) {
            pathPatterns.addAll(0, DEFAULT_PATH_PATTERNS);
            excludePathPatterns.addAll(0, DEFAULT_EXCLUDE_PATH_PATTERNS);
        }
    }
}
