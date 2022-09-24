-- ----------------------------
-- 字典数据表
-- ----------------------------
create table `sys_st_dict` (
  `id` bigint(20) not null auto_increment comment '主键',
  `dict_code` varchar(100) not null comment '字典编码',
  `dict_sort` int(4) default '0' comment '字典排序',
  `dict_value` varchar(100) default '' comment '字典编码中文',
  `dict_type` varchar(100) default '' comment '字典类型',
  `status` char(1) default '0' comment '状态（0正常 1停用）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`id`),
  unique key `idx_dict_code_type_unique` (`dict_code`,`dict_type`)
) engine=innodb auto_increment=1 default charset=utf8mb4 comment='字典数据表';

insert into `sys_st_dict` (`id`, `dict_code`, `dict_sort`, `dict_value`, `dict_type`, `status`, `create_time`, `update_time`, `remark`) values (1, '1', 0, '男', 'gender', '0', '2022-09-24 17:35:38', '2022-09-24 17:36:30', null);
insert into `sys_st_dict` (`id`, `dict_code`, `dict_sort`, `dict_value`, `dict_type`, `status`, `create_time`, `update_time`, `remark`) values (2, '2', 0, '女', 'gender', '0', '2022-09-24 17:35:38', '2022-09-24 17:35:38', null);

-- ----------------------------
-- 字典类型表
-- ----------------------------
create table `sys_st_dict_type` (
  `id` bigint(20) not null auto_increment comment '主键',
  `dict_name` varchar(100) default '' comment '字典名称',
  `dict_type` varchar(100) default '' comment '字典类型',
  `status` char(1) default '0' comment '状态（0正常 1停用）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`id`),
  unique key `idx_dict_type_unique` (`dict_type`)
) engine=innodb auto_increment=1 default charset=utf8mb4 comment='字典类型表';


insert into `sys_st_dict_type` (`id`, `dict_name`, `dict_type`, `status`, `create_time`, `update_time`, `remark`) values (1, '性别', 'gender', '0', '2022-09-24 17:34:28', '2022-09-24 18:09:12', null);
