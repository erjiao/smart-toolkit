package com.bmsoft.toolkit.web.handler;

import com.bmsoft.toolkit.core.Code;
import com.bmsoft.toolkit.core.Result;
import com.bmsoft.toolkit.core.annotation.UseBySpringBean;
import com.bmsoft.toolkit.core.exception.BusinessException;
import com.bmsoft.toolkit.core.exception.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author llk
 * @date 2019-10-09 13:37
 * <p>
 * 当指定 {@code @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)} 时,
 * Feign 调用时 springboot 会自动抛出运行时异常, 会使得调用方无法获取 Result
 */
@Slf4j
@UseBySpringBean
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     * <p>
     * 在 application.properties 配置上面信息才会起作用
     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Result handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
//        log.error("找不到对应请求", e);
//        return Result.error(Code.NOT_FOUND, request.getRequestURI());
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                        HttpServletRequest request) {
        log.error("参数解析失败", e);
        return Result.error(Code.BAD_REQUEST, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                HttpServletRequest request) {
        log.error("缺少请求参数", e);
        return Result.error(Code.BAD_REQUEST, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder builder = new StringBuilder(128);
        for (FieldError fieldError : fieldErrors) {
            builder.append("[")
                    .append(fieldError.getField())
                    .append("]")
                    .append(fieldError.getDefaultMessage())
                    .append(";");
        }
        if (builder.length() <= 0) {
            builder.append(Code.BAD_REQUEST.getMsg());
        }
        log.error("参数校验失败: {}", builder.toString());
        return Result.error(Code.BAD_REQUEST, builder.toString(), request.getRequestURI());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleConstraintViolationException(ConstraintViolationException e,
                                                     HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder(128);
        for (ConstraintViolation<?> item : violations) {
            sb.append(item.getPropertyPath().toString())
                    .append(" input invalid value: ")
                    .append(item.getInvalidValue())
                    .append(". error: ")
                    .append(item.getMessage())
                    .append(";");
        }
        if (sb.length() <= 0) {
            sb.append(Code.BAD_REQUEST.getMsg());
        }
        log.error("参数校验失败: {}", sb.toString());
        return Result.error(Code.BAD_REQUEST, sb.toString(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                               HttpServletRequest request) {
        log.error("不支持当前请求方法", e);
        return Result.error(Code.METHOD_NOT_ALLOWED, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpMediaTypeNotSupportedException e,
                                                               HttpServletRequest request) {
        log.error("不支持当前媒体类型", e);
        return Result.error(Code.UNSUPPORTED_MEDIA_TYPE, request.getRequestURI());
    }

    /**
     * 当指定 {@code @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)} 时, feign 调用时 springboot 会自动抛出运行时异常
     * 会使调用方无法获取 Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public Result handleFeignException(FeignException e) {
        log.error("服务间调用异常", e);
        return e.getResult();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public Result handleHttpRequestMethodNotSupportedException(BusinessException e,
                                                               HttpServletRequest request) {
        log.error("业务异常", e);
        return Result.error(e.getCode(), e.getMessage(), request.getRequestURI());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnsupportedOperationException.class)
    public Result handleUnsupportedOperationException(UnsupportedOperationException e, HttpServletRequest request) {
        log.error("不支持的数据类型", e);
        return Result.error(Code.ERROR, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        log.error("系统繁忙", e);
        return Result.error(Code.ERROR, e.getMessage(), request.getRequestURI());
    }
}
