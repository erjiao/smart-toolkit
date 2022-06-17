package com.bmsoft.toolkit.properties;

import lombok.Data;

import java.util.List;

/**
 * @author llk
 * @date 2022-06-17 14:54
 */
@Data
public class InterceptorProperties {

    private List<String> pathPatterns;

    private List<String> excludePathPatterns;

    private Integer order;

}
