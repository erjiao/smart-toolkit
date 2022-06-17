package com.bmsoft.toolkit.annotation;

import com.bmsoft.toolkit.config.AccessCtrlConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author llk
 * @date 2022-06-18 01:05
 */
public class AccessCtrlDeferredSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{AccessCtrlConfiguration.class.getName()};
    }

}
