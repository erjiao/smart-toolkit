package com.bmsoft.toolkit.interceptor;

import cn.hutool.core.util.StrUtil;
import com.bmsoft.toolkit.annotation.UseBySpringBean;
import com.bmsoft.toolkit.holder.BaseContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import static com.bmsoft.toolkit.constant.RequestHeaderConstant.PAGE_NUM_KEY;
import static com.bmsoft.toolkit.constant.RequestHeaderConstant.PAGE_SIZE_KEY;


/**
 * 填充 BaseContextHolder 本地线程变量拦截器
 *
 * @author llk
 * @date 2019-11-10 03:54
 */
@UseBySpringBean
public class BaseContextHolderInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) {
        // --- 分页信息
        String pageNum = request.getParameter(PAGE_NUM_KEY);
        String pageSize = request.getParameter(PAGE_SIZE_KEY);
        if (StrUtil.isNotBlank(pageNum) || StrUtil.isNotBlank(pageSize)) {
            BaseContextHolder.set(PAGE_NUM_KEY, pageNum);
            BaseContextHolder.set(PAGE_SIZE_KEY, pageSize);
        }

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
