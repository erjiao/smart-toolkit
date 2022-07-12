package com.bmsoft.toolkit.mybatis.entity;

import java.util.Collections;
import java.util.List;

/**
 * @author llk
 * @date 2022-07-12 17:23
 */
public class EmptyDictContainer extends AbstractDictContainer {

    @Override
    public List<Dict> provide() {
        return Collections.emptyList();
    }
}
