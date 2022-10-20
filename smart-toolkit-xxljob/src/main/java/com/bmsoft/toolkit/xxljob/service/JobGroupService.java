package com.bmsoft.toolkit.xxljob.service;

import com.bmsoft.toolkit.xxljob.model.XxlJobGroup;

import java.util.List;

public interface JobGroupService {

    List<XxlJobGroup> getJobGroup();

    boolean autoRegisterGroup();

    boolean preciselyCheck();

}
