package com.bmsoft.toolkit.xxljob.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bmsoft.toolkit.xxljob.config.XxlJobProperties;
import com.bmsoft.toolkit.xxljob.model.XxlJobGroup;
import com.bmsoft.toolkit.xxljob.service.JobGroupService;
import com.bmsoft.toolkit.xxljob.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:34
 * @version: 1.0
 */
@Service
@DependsOn("xxlJobExecutor")
public class JobGroupServiceImpl implements JobGroupService {

    @Autowired
    public JobGroupServiceImpl(JobLoginService jobLoginService, XxlJobProperties xxlJobProperties) {
        this.jobLoginService = jobLoginService;

        this.adminAddresses = xxlJobProperties.getAdmin().getAddresses();
        this.appName = xxlJobProperties.getExecutor().getAppname();
        this.title = xxlJobProperties.getExecutor().getTitle();
    }

    private String adminAddresses;

    private String appName;

    private String title;

    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobGroup> getJobGroup() {
        String url = adminAddresses + "/jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", appName)
                .form("title", title)
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);

        return array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean autoRegisterGroup() {
        String url = adminAddresses + "/jobgroup/save";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", appName)
                .form("title", title)
                .cookie(jobLoginService.getCookie())
                .execute();
        Object code = JSONUtil.parse(response.body()).getByPath("code");
        return code.equals(200);
    }

    @Override
    public boolean preciselyCheck() {
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(appName)
                        && xxlJobGroup.getTitle().equals(title))
                .findAny();
        return has.isPresent();
    }

}
