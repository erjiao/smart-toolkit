package com.bmsoft.toolkit.interceptor;

import com.bmsoft.toolkit.annotation.UseBySpringBean;
import com.bmsoft.toolkit.exception.InvalidIpException;
import com.bmsoft.toolkit.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author llk
 * @date 2020-08-17 10:10
 */
@Slf4j
@UseBySpringBean
public class IpInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AccessCtrl accessCtrl;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String realIP = IPUtils.getRealIP(request);
        log.info("ip: {}", realIP);
        if (accessCtrl.isEnable()) {
            Set<String> whiteIps = accessCtrl.getWhiteIps();
            Set<String> blackIps = accessCtrl.getBlackIps();
            if (!whiteIps.contains(realIP) || blackIps.contains(realIP)) {
                throw new InvalidIpException(realIP);
            }
        }
        return true;
    }

    /**
     * IP 访问控制接口
     */
    public interface AccessCtrl {

        boolean isEnable();

        Set<String> getWhiteIps();

        Set<String> getBlackIps();
    }

}
