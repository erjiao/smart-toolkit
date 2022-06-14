package com.bmsoft.toolkit.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;


/**
 * @author llk
 * @date 2019-10-09 18:21
 */
public class JsonUtils {

    private JsonUtils() {}

    /**
     * 实体转为json字符串
     *
     * @param o
     * @return
     */
    @SneakyThrows
    public static String toJson(@NonNull Object o) {
        return ObjContainer.objectMapper.writeValueAsString(o);
    }

    /**
     * 实体转换为格式化的json字符串
     *
     * @param o
     * @return
     */
    @SneakyThrows
    public static String toPrettyJson(@NonNull Object o) {
        return ObjContainer.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    /**
     * json 字符串转为实体
     *
     * @param json json 字符串
     * @param clazz 实体class
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T toBean(@NonNull String json, Class<T> clazz) {
        return ObjContainer.objectMapper.readValue(json, clazz);
    }

    /**
     * json 字符串转为 map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(@NonNull String json) {
        return to(json, new TypeReference<Map<String, Object>>(){});
    }

    /**
     * json 数组字符串转为 list 集合
     *
     * @param json
     * @param elementsClass
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> List<T> toList(@NonNull String json, Class<T> elementsClass) {
        JavaType javaType = ObjContainer.objectMapper.getTypeFactory().constructParametricType(List.class, elementsClass);
        return ObjContainer.objectMapper.readValue(json, javaType);

    }

    /**
     * json 字符串转为 typeReference 中声明的泛型
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T to(@NonNull String json, TypeReference<T> typeReference) {
        return ObjContainer.objectMapper.readValue(json, typeReference);
    }

    /**
     * 是否为JSON字符串，首尾都为大括号或中括号判定为JSON字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     */
    public static boolean isJson(String str) {
        return isJsonObj(str) || isJsonArray(str);
    }

    /**
     * 是否为JSONObject字符串，首尾都为大括号判定为JSONObject字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     */
    public static boolean isJsonObj(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return StrUtil.isWrap(str.trim(), '{', '}');
    }

    /**
     * 是否为JSONArray字符串，首尾都为中括号判定为JSONArray字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 3.3.0
     */
    public static boolean isJsonArray(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return StrUtil.isWrap(str.trim(), '[', ']');
    }

}
