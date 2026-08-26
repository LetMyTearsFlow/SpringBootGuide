package org.cqlin.datavalidationdemo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final Pattern MAINLAND_CHINA_MOBILE = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * Checks whether a non-null value is a valid mainland China mobile number.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || MAINLAND_CHINA_MOBILE.matcher(value).matches();
    }
}
