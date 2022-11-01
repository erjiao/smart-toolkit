package com.bmsoft.toolkit.excel;

import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类型保持
 *
 * 解决因为list数据为空, 无法获取 T 类型的问题
 *
 * @author llk
 * @date 2019-12-02 09:45
 */
public abstract class ExcelDataRef<T> {

    /** 泛型参数 */
    private final Type type;

    /**
     * list 数据
     */
    private List<T> list;

    /**
     * 构造
     */
    public ExcelDataRef() {
        this(new ArrayList<>());
    }

    public ExcelDataRef(List<T> list) {
        this.list = list;
        this.type = TypeUtil.getTypeArgument(getClass());
    }

    /**
     * 获取用户定义的泛型参数
     *
     * @return 泛型参数
     */
    public Type getType() {
        return this.type;
    }

    /**
     * 获取泛型 T 的集合
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置 list 集合
     */
    public ExcelDataRef<T> list(List<T> list) {
        this.list = list;
        return this;
    }
}
