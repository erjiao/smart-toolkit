package com.bmsoft.toolkit.core.holder;

import com.bmsoft.toolkit.core.Dict;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author llk
 * @date 2022-09-24 03:16
 */
@Slf4j
public final class DictHolder {

    private List<Dict> dictList = new ArrayList<>();

    private Map<String, List<Dict>> container = new HashMap<>();

    private Map<String, Map<String, Dict>> containerMap = new HashMap<>();

    public synchronized void init(List<Dict> dictList) {
        // deep copy
        this.dictList = new ArrayList<>(dictList);
        // 排序
        this.container =
                this.dictList.stream().sorted(Comparator.comparingInt(Dict::getSort)).collect(Collectors.groupingBy(Dict::getType));
        this.containerMap =
                this.dictList.stream().collect(Collectors.groupingBy(Dict::getType, Collectors.toMap(Dict::getCode, Function.identity(), (p, n) -> n)));
    }

    public synchronized Dict get(String type, String code) {
        Map<String, Dict> dictMap = containerMap.get(type);
        if (Objects.isNull(dictMap)) {
            log.warn("The type [{}] dict is empty, Check whether the type dict has code list.", type);
            return new Dict();
        }
        return dictMap.getOrDefault(code, new Dict());
    }

    public synchronized String getValue(String type, String code) {
        return get(type, code).getValue();
    }

    public synchronized List<Dict> getByType(String type) {
        return container.getOrDefault(type, new ArrayList<>());
    }

    public List<Dict> getAll() {
        return dictList;
    }

    public Integer size() {
        return dictList.size();
    }




    private static final DictHolder INSTANCE = new DictHolder();


    private DictHolder() {
    }

    public static DictHolder getInstance() {
        return INSTANCE;
    }
}
