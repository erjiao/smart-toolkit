package com.bmsoft.toolkit.mybatis.support;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bmsoft.toolkit.core.Dict;
import com.bmsoft.toolkit.core.holder.DictHolder;
import com.bmsoft.toolkit.mybatis.dao.SysStDictMapper;
import com.bmsoft.toolkit.mybatis.dao.SysStDictTypeMapper;
import com.bmsoft.toolkit.mybatis.entity.SysStDict;
import com.bmsoft.toolkit.mybatis.entity.SysStDictType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author llk
 * @date 2022-09-24 00:37
 */
public class SysStDictService extends ServiceImpl<SysStDictMapper, SysStDict> {

    @Autowired
    SysStDictMapper sysStDictMapper;

    @Autowired
    SysStDictTypeMapper sysStDictTypeMapper;


    /**
     * 关联sys_st_dict_type 获取 status = 0 的字典项
     *
     * @return
     */
    public List<SysStDict> getNormalDictList() {
        List<SysStDict> dictList = sysStDictMapper.selectNormalDictList();
        fillEachChildren(dictList);
        return dictList;
    }



    /**
     * 根据字典类型获取字典列表
     *
     * @param dictType
     * @return
     */
    public List<SysStDict> getNormalDictListByType(String dictType) {
        if (StrUtil.isBlank(dictType)) {
            return this.getNormalDictList();
        }

        LambdaQueryWrapper<SysStDict> queryWrapper = Wrappers.<SysStDict>lambdaQuery()
                .eq(SysStDict::getDictType, dictType)
                .eq(SysStDict::getStatus, "0");
        List<SysStDict> dictList = sysStDictMapper.selectList(queryWrapper);
        fillEachChildren(dictList);
        return dictList;
    }

    /**
     * 关联sys_st_dict_type 获取所有字典项(含status=1)
     *
     * @return
     */
    public List<SysStDict> getDictList() {
        List<SysStDict> dictList = sysStDictMapper.selectAll();
        fillEachChildren(dictList);
        return dictList;
    }


    public List<SysStDict> getHasParentNormalDictList() {
        return sysStDictMapper.selectHasParentNormalDictList();
    }

    /**
     * 获取 status = 0 的 dictType
     *
     * @return
     */
    public List<SysStDictType> getNormalDictTypeList() {
        LambdaQueryWrapper<SysStDictType> queryWrapper =
                Wrappers.<SysStDictType>lambdaQuery().eq(SysStDictType::getStatus, '0');
        return sysStDictTypeMapper.selectList(queryWrapper);
    }

    /**
     * 获取所有 dictType
     *
     * @return
     */
    public List<SysStDictType> getDictTypeList() {
        return sysStDictTypeMapper.selectList(null);
    }


    // DictHolder 初始化, 用于 TranslationInterceptor 翻译
    private void initDictHolder() {
        List<SysStDict> l = this.getNormalDictList();
        List<Dict> d = l.stream().map(this::transferTo).collect(Collectors.toList());

        DictHolder.getInstance().init(d);
    }

    private Dict transferTo(SysStDict current) {
        Dict dict = new Dict();
        dict.setCode(current.getDictCode());
        dict.setValue(current.getDictValue());
        dict.setType(current.getDictType());
        dict.setSort(current.getDictSort());

        // children 赋值
        List<SysStDict> children = current.getChildren();
        for (SysStDict child : children) {
            dict.getChildren().add(transferTo(child));
        }
        return dict;
    }

    private void fillEachChildren(List<SysStDict> dicts) {
        List<SysStDict> allChildren = this.getHasParentNormalDictList();
        for (SysStDict current : dicts) {
            fillChildren(current, allChildren);
        }
    }

    private SysStDict fillChildren(SysStDict current, List<SysStDict> remains) {
        for (SysStDict remain : remains) {
            if (hasChildren(current, remains) && current.getId().equals(remain.getParentId())) {
                current.getChildren().add(fillChildren(remain, remains));
            }
        }
        return current;
    }

    private boolean hasChildren(SysStDict current, List<SysStDict> remains) {
        return remains.size() > 0 &&
                remains.stream().anyMatch(c -> c.getParentId().equals(current.getId()));
    }

}
