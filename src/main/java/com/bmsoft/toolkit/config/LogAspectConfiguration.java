package com.bmsoft.toolkit.config;

import com.bmsoft.toolkit.utils.IdUtils;
import com.bmsoft.toolkit.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author llk
 * @date 6/15/22 11:39 PM
 */
@Configuration
@ConditionalOnProperty(value = "toolkit.log-aspect.controller-expression")
public class LogAspectConfiguration {


    @Value("${toolkit.log-aspect.controller-expression}")
    private String controllerExpression;  // eg: execution(* com.bmsoft.smart..*Controller.*(..))


    private static final String REQUEST_EXPRESSION = " && " +
                    "(@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.DeleteMapping))";

    @Bean
    public Advisor logAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(controllerExpression + REQUEST_EXPRESSION);
        advisor.setAdvice(new LogAdvice());
        return advisor;
    }

    @Slf4j
    static class LogAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String requestId = IdUtils.get32UUID();
            Instant begin = Instant.now();
            Object[] args = invocation.getArguments();
            Object target = invocation.getThis();
            String className = target.getClass().getName();
            Method method = invocation.getMethod();
            String methodName = method.getName();

            String[] parameterNames = Stream.of(method.getParameters()).map(Parameter::getName).toArray(String[]::new);
            Map<String, Object> paramMap = buildParamMap(args, parameterNames);
            log.info("REQUEST BEGIN [{}], execute method [{}], args = {}" , requestId,className + "#" + methodName, JsonUtils.toJson(paramMap));
            Object proceed;
            try {
                // before
                proceed = invocation.proceed();
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

}
