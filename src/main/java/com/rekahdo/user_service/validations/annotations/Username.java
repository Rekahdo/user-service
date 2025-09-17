package com.rekahdo.user_service.validations.annotations;

import com.rekahdo.user_service.validations.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "Username is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}