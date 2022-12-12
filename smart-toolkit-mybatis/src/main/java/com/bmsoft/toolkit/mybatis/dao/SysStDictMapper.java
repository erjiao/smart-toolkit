package com.bmsoft.toolkit.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bmsoft.toolkit.mybatis.entity.SysStDict;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author llk
 * @date 2022-09-24 00:37
 */
public interface SysStDictMapper extends BaseMapper<SysStDict> {


    @Select("select t.*, t1.dict_name from sys_st_dict t inner join sys_st_dict_type t1 on t.dict_type = t1.dict_type where t.status = '0' and t1.status = '0'")
    List<SysStDict> selectNormalDictList();


    @Select("select t.*, t1.dict_name from sys_st_dict t inner join sys_st_dict_type t1 on t.dict_type = t1.dict_type")
    List<SysStDict> selectAll();


    @Select("select t.*, t1.dict_name from sys_st_dict t inner join sys_st_dict_type t1 on t.dict_type = t1.dict_type where t.status = '0' and t1.status = '0' and t.parent_id != -1")
    List<SysStDict> selectHasParentNormalDictList();

}
