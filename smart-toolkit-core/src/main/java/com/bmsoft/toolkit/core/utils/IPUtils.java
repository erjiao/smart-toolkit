package com.bmsoft.toolkit.core.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

    private static final String UNKNOWN = "unknown";
    private static final String SEPARATOR = ",";

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isValidIp(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(SEPARATOR)) {
                ip = ip.split(SEPARATOR)[0];
            }
        }
        if (!isValidIp(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (!isValidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (!isValidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!isValidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!isValidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!isValidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        return ip;
    }

    private static boolean isValidIp(String ip) {
        return StrUtil.isNotBlank(ip) && !UNKNOWN.equals(ip);
    }
}
