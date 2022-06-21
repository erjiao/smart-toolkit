package com.bmsoft.toolkit.core.utils;

import java.util.Map;

/**
 * @author llk
 * @date 2020-07-17 00:02
 */
public class PlaceHolderUtils {

    /**
     * 前缀占位符
     */
    public static final String PLACEHOLDER_PREFIX = "{";

    /**
     * 后缀占位符
     */
    public static final String PLACEHOLDER_SUFFIX = "}";


    public static String resolve(String content, Map<String, String> valueMap) {
        int start = content.indexOf(PLACEHOLDER_PREFIX);
        if (start == -1) {
            return content;
        }
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(PLACEHOLDER_SUFFIX, start);
            // 获取占位符属性值，如{id}, 即获取id
            String placeholder = result.substring(start + PLACEHOLDER_PREFIX.length(), end);
            // 替换整个占位符内容，即将{id}值替换为替换规则回调中的内容
            String replaceContent = placeholder.trim().isEmpty() ? "" : valueMap.get(placeholder);
            result.replace(start, end + PLACEHOLDER_SUFFIX.length(), replaceContent);
            start = result.indexOf(PLACEHOLDER_PREFIX, start + replaceContent.length());
        }
        return result.toString();
    }

}
