package com.bmsoft.toolkit.mybatis.support;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @author llk
 * @date 2022-07-07 14:19
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("updateTime", now, metaObject, false);

        // TODO: 7/7/22 设置用户功能待实现
//        fillValIfNullByName("createBy", getUserName(), metaObject, false);
//        fillValIfNullByName("updateBy", getUserName(), metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        fillValIfNullByName("updateTime", now, metaObject, true);
//        fillValIfNullByName("updateBy", getUserName(), metaObject, true);
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    protected static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 1. 没有 get 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值并且不允许覆盖
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }
}
