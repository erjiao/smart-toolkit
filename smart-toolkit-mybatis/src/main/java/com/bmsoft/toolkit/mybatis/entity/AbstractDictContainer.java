package com.bmsoft.toolkit.mybatis.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author llk
 * @date 2022-07-11 10:38
 */
@Getter
public abstract class AbstractDictContainer implements DictContainer {

    private List<Dict> dictList = new ArrayList<>();

    private Map<String, List<Dict>> container = new HashMap<>();

    private Map<String, Map<String, Dict>> containerMap = new HashMap<>();

    public AbstractDictContainer() {
        init(provide());
    }

    @Override
    public synchronized void init(List<Dict> dictList) {
        this.dictList = dictList;
        this.container = this.dictList.stream().collect(Collectors.groupingBy(Dict::getType));
        this.containerMap = this.dictList.stream().collect(Collectors.groupingBy(Dict::getType, Collectors.toMap(Dict::getCode, Function.identity(), (p, n) -> n)));
    }

    @Override
    public synchronized void store(Dict dict) {
        throw new UnsupportedOperationException("not allowed");
    }

    @Override
    public synchronized Dict get(String type, String code) {
        Map<String, Dict> dictMap = containerMap.get(type);
        return dictMap.getOrDefault(code, new Dict());
    }

    @Override
    public List<Dict> getAll() {
        return dictList;
    }

    @Override
    public Integer size() {
        return dictList.size();
    }


    public abstract List<Dict> provide();

}
