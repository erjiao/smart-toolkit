package com.bmsoft.toolkit.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author llk
 * @date 2022-09-25 00:51
 */
@Data
@TableName("sys_st_dict_type")
@ApiModel(value="SysStDictType对象", description="字典类型表")
public class SysStDictType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "字典名称")
    @TableField("`dict_name`")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    @TableField("`dict_type`")
    private String dictType;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    @TableField("`status`")
    private String status;

    @ApiModelProperty(value = "备注")
    @TableField("`remark`")
    private String remark;


}
