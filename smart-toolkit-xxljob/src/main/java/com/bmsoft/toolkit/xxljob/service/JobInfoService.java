package com.bmsoft.toolkit.xxljob.service;

import com.bmsoft.toolkit.xxljob.model.XxlJobInfo;

import java.util.List;

public interface JobInfoService {

    List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler);

    Integer addJobInfo(XxlJobInfo xxlJobInfo);

}
