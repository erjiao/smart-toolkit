package com.bmsoft.toolkit.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.bmsoft.toolkit.core.annotation.UseBySpringBean;
import com.bmsoft.toolkit.core.holder.ParamPageContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import static com.bmsoft.toolkit.web.constant.RequestHeaderConstant.PAGE_NUM_KEY;
import static com.bmsoft.toolkit.web.constant.RequestHeaderConstant.PAGE_SIZE_KEY;

/**
 * @author llk
 * @date 2022-06-16 19:06
 */
@UseBySpringBean
public class ParamPageContextInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) throws Exception {
        String pageNum = request.getParameter(PAGE_NUM_KEY);
        String pageSize = request.getParameter(PAGE_SIZE_KEY);
        if (!StrUtil.isBlank(pageNum) && !StrUtil.isBlank(pageSize)) {
            ParamPageContextHolder.set(Long.valueOf(pageNum), Long.valueOf(pageSize));
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        ParamPageContextHolder.clear();
    }
}
