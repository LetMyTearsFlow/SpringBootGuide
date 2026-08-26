package org.cqlin.datavalidationdemo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    /**
     * Returns the message bundle key used when validation fails.
     */
    String message() default "{user.phone.invalid}";

    /**
     * Returns the validation groups associated with this constraint.
     */
    Class<?>[] groups() default {};

    /**
     * Returns custom metadata payload types for this constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
