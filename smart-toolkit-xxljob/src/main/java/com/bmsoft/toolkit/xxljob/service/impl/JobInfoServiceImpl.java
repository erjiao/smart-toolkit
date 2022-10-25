package com.bmsoft.toolkit.xxljob.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bmsoft.toolkit.xxljob.config.XxlJobProperties;
import com.bmsoft.toolkit.xxljob.model.XxlJobInfo;
import com.bmsoft.toolkit.xxljob.service.JobInfoService;
import com.bmsoft.toolkit.xxljob.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/20 10:36
 * @version: 1.0
 */
@Service
@DependsOn("xxlJobExecutor")
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    public JobInfoServiceImpl(JobLoginService jobLoginService, XxlJobProperties xxlJobProperties) {
        this.jobLoginService = jobLoginService;
        this.adminAddresses = xxlJobProperties.getAdmin().getAddresses();
    }

    private String adminAddresses;

    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        String url = adminAddresses + "/jobinfo/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);

        return array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url = adminAddresses + "/jobinfo/add";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();

        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        if (code.equals(200)) {
            return Convert.toInt(json.getByPath("content"));
        }
        throw new RuntimeException("add jobInfo error!");
    }

}
