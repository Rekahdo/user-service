package com.rekahdo.user_service.validations.validators;

import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<Number, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;

        if(value.startsWith("0")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Number should not start with '0'").addConstraintViolation();
            return false;
        }

        if(!value.matches("^[1-9]\\d{9}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Number should only contain 10 digits").addConstraintViolation();
            return false;
        }

        return true;
    }

}
