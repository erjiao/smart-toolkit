package com.bmsoft.toolkit.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author llk
 * @date 2022-07-08 17:53
 */
@Data
public class Dict {

    /* 字典类型 */
    private String type;

    /* 字典编码 */
    private String code;

    /* 字典值 */
    private String value;

    /* 排序 */
    private Integer sort;

    /* 子节点列表 */
    private List<Dict> children = new ArrayList<>();

    /**
     * label, 值等同于value
     * @return
     */
    public String getLabel() {
        return value;
    }
}
