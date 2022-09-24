package com.bmsoft.toolkit.mybatis.support;

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
     * @return
     */
    public List<SysStDict> getNormalDictList() {
        return sysStDictMapper.selectNormalDictList();
    }

    /**
     * 关联sys_st_dict_type 获取所有字典项(含status=1)
     *
     * @return
     */
    public List<SysStDict> getDictList() {
        return sysStDictMapper.selectAll();
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

    private Dict transferTo(SysStDict sysStDict) {
        Dict dict = new Dict();
        dict.setCode(sysStDict.getDictCode());
        dict.setValue(sysStDict.getDictValue());
        dict.setType(sysStDict.getDictType());
        dict.setSort(sysStDict.getDictSort());
        return dict;
    }
}
