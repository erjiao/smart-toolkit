package com.bmsoft.toolkit.validator;


import com.bmsoft.toolkit.annotation.LegalValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author llk
 * @date 2020-02-11 14:20
 */
public class LegalValueValidatorForString implements ConstraintValidator<LegalValue, String> {

    private String[] allowableValues;

    private boolean nullable;

    @Override
    public void initialize(LegalValue legalValue) {
        this.allowableValues = legalValue.allowableValues();
        this.nullable = legalValue.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable && Objects.isNull(value)) {
            return true;
        }
        for (String allowableValue : allowableValues) {
            if (allowableValue.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
