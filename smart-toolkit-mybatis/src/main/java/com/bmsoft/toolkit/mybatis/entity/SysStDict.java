package com.bmsoft.toolkit.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author llk
 * @date 2022-09-24 00:37
 */
@Data
@TableName("sys_st_dict")
@ApiModel(value="SysStDict对象", description="字典数据表")
public class SysStDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "字典编码")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "字典排序")
    @TableField("dict_sort")
    private Integer dictSort;

    @ApiModelProperty(value = "字典编码中文")
    @TableField("dict_value")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "父id, 默认值 -1")
    @TableField("parent_id")
    private Long parentId = -1L;

    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(value = "update_time")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "字典名称")
    @TableField(exist = false)
    private String dictName;

    @ApiModelProperty(value = "子字典列表")
    @TableField(exist = false)
    private List<SysStDict> children = new ArrayList<>();


}
