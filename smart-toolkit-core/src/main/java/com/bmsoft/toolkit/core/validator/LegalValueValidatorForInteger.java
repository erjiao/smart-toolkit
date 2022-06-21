package com.bmsoft.toolkit.core.validator;


import com.bmsoft.toolkit.core.annotation.LegalValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author llk
 * @date 2020-02-11 14:20
 */
public class LegalValueValidatorForInteger implements ConstraintValidator<LegalValue, Integer> {

    private String[] allowableValues;

    private boolean nullable;

    @Override
    public void initialize(LegalValue legalValue) {
        this.allowableValues = legalValue.allowableValues();
        this.nullable = legalValue.nullable();

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (nullable && Objects.isNull(value)) {
            return true;
        }
        for (String allowableValue : allowableValues) {
            if (allowableValue.equals(String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }

}
