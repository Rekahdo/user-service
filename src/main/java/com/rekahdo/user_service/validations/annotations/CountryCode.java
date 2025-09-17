package com.rekahdo.user_service.validations.annotations;

import com.rekahdo.user_service.validations.validators.CountryCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryCode {

    String message() default "Invalid country code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}