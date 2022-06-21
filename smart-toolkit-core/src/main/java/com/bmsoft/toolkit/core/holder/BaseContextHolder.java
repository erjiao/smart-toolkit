package com.bmsoft.toolkit.core.holder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author llk
 * @date 2019-11-10 01:23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseContextHolder {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }


    public static void clear() {
        threadLocal.remove();
    }

}
