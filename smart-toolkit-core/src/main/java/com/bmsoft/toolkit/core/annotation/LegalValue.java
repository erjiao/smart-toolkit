package com.bmsoft.toolkit.core.annotation;


import com.bmsoft.toolkit.core.validator.LegalValueValidatorForInteger;
import com.bmsoft.toolkit.core.validator.LegalValueValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 目前可以验证String以及Integer 的值
 *
 * @author llk
 * @date 2020-02-11 14:07
 */
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LegalValueValidatorForInteger.class, LegalValueValidatorForString.class})
public @interface LegalValue {

    String[] allowableValues();

    /**
     * 可否为空, 默认 false
     */
    boolean nullable() default false;

    String message() default "不合法的值";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
