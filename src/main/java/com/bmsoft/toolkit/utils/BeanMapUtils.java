package com.bmsoft.toolkit.utils;

import net.sf.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author llk
 * @date 2019-10-09 22:12
 */
public class BeanMapUtils {

    private BeanMapUtils() {
    }

    /**
     *
     * @param bean 实体
     * @return map
     */
    public static <T> Map<String, Object> bean2Map(T bean) {
        return bean2Map(bean, true);
    }

    public static <T> Map<String, Object> bean2MapNoNull(T bean) {
        return bean2Map(bean, false);
    }

    private static <T> Map<String, Object> bean2Map(T bean, boolean needNull) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object o = beanMap.get(key);
                if (needNull || !Objects.isNull(o)) {
                    map.put(String.valueOf(key), o);
                }
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return t
     */
    public static <T> T map2Bean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        return map2Bean(map, t);
    }

    public static <T> List<T> maps2Beans(List<Map<String, Object>> maps, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (Map<String, Object> m : maps) {
                map = m;
                bean = clazz.newInstance();
                map2Bean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (ValidateUtils.isValid(beans)) {
            Map<String, Object> map;
            T bean;
            for (T t : beans) {
                bean = t;
                map = bean2Map(bean);
                list.add(map);
            }
        }
        return list;
    }
}
