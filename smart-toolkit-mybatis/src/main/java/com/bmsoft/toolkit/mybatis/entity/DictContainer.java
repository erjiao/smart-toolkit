package com.bmsoft.toolkit.mybatis.entity;

import java.util.List;

/**
 * @author llk
 * @date 2022-07-08 17:46
 */
public interface DictContainer {

    void init(List<Dict> dictList);

    void store(Dict dict);

    Dict get(String type, String code);

    List<Dict> getAll();

    Integer size();

}
