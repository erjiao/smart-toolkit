package com.bmsoft.toolkit.xxljob.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.bmsoft.toolkit.xxljob.config.XxlJobProperties;
import com.bmsoft.toolkit.xxljob.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:49
 * @version: 1.0
 */
@Service
@DependsOn("xxlJobExecutor")
public class JobLoginServiceImpl implements JobLoginService {

    @Autowired
    public JobLoginServiceImpl(XxlJobProperties xxlJobProperties) {
        XxlJobProperties.Admin admin = xxlJobProperties.getAdmin();
        this.adminAddresses = admin.getAddresses();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
    }

    private String adminAddresses;

    private String username;

    private String password;

    private final Map<String, String> loginCookie = new HashMap<>();

    @Override
    public void login() {
        String url = adminAddresses + "/login";
        HttpResponse response = HttpRequest.post(url)
                .form("userName", username)
                .form("password", password)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new RuntimeException("get xxl-job cookie error!");

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);
    }

    @Override
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr != null) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }


}
