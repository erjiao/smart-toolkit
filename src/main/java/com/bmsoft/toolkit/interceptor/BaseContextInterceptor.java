package com.bmsoft.toolkit.interceptor;

import com.bmsoft.toolkit.annotation.UseBySpringBean;
import com.bmsoft.toolkit.holder.BaseContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;


/**
 * 填充 BaseContextHolder 本地线程变量拦截器
 *
 * @author llk
 * @date 2019-11-10 03:54
 */
@UseBySpringBean
public class BaseContextInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) {

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) {
        // 清除线程变量
        BaseContextHolder.clear();
    }
}
