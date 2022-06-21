package com.bmsoft.toolkit.web.annotation;

import com.bmsoft.toolkit.web.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册 GlobalExceptionHandler 类
 *
 * @see GlobalExceptionHandler
 *
 * @author llk
 * @date 2022-06-16 14:51
 */
public class GlobalExceptionHandlerSelector implements ImportSelector {


    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{GlobalExceptionHandler.class.getName()};
    }

}
