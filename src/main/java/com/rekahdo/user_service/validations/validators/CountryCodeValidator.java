package com.rekahdo.user_service.validations.validators;

import com.rekahdo.user_service.validations.annotations.CountryCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryCodeValidator implements ConstraintValidator<CountryCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;

        if(value.contains("+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Country code should not contain a '+' sign").addConstraintViolation();
            return false;
        }

        if(!value.matches("^\\d{1,3}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Country code must be within 1-3 digits length. E.g 1, 12 or 234").addConstraintViolation();
            return false;
        }

        return true;
    }

}
