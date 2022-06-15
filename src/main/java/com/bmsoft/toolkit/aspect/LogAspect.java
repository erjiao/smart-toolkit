package com.bmsoft.toolkit.aspect;


import com.bmsoft.toolkit.annotation.UseBySpringBean;
import com.bmsoft.toolkit.utils.IdUtils;
import com.bmsoft.toolkit.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 日志切面
 *
 * @author llk
 * @date 2019-10-09 13:35
 */
@Slf4j
@Aspect
@UseBySpringBean
public class LogAspect {

    @Pointcut("execution(* com.bmsoft.dc..*Controller.*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        String requestId = IdUtils.get32UUID();
        Instant begin = Instant.now();
        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        String className = target.getClass().getName();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getName();
        String[] parameterNames = signature.getParameterNames();
        Map<String, Object> paramMap = buildParamMap(args, parameterNames);
        log.info("REQUEST BEGIN [{}], execute method [{}], args = {}" , requestId,className + "#" + methodName, JsonUtils.toJson(paramMap));
        Object proceed;
        try {
            // before
            proceed = pjp.proceed();
            Instant end = Instant.now();
            log.info("REQUEST END   [{}], cost time {} ms", requestId, Duration.between(begin, end).toMillis());
            // after
            return proceed;
            // return
        } catch (Throwable e) {
            Instant end = Instant.now();
            log.error("REQUEST ERROR END [{}], cost time {} ms", requestId, Duration.between(begin, end).toMillis());
            // throwing
            throw e;
        }
    }

    private Map<String, Object> buildParamMap(Object[] args, String[] parameterNames) {
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest
                    || args[i] instanceof HttpServletResponse
                    || args[i] instanceof MultipartFile
                    || args[i] instanceof MultipartFile[] ) {
                continue;
            }
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

}
