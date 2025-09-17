package com.rekahdo.user_service.validations.validators;

import com.rekahdo.user_service.validations.annotations.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.passay.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMin = 8;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMax = Integer.MAX_VALUE;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int alphabetCount = 5;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int uppercaseCount = 1;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int digitCount = 1;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int specialCount = 1;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final PasswordValidator passwordValidator;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private RuleResult result;

    public StrongPasswordValidator() {
        passwordValidator = new PasswordValidator(
                new LengthRule(lenMin, lenMax),
                new CharacterRule(EnglishCharacterData.Alphabetical, alphabetCount),
                new CharacterRule(EnglishCharacterData.UpperCase, uppercaseCount),
                new CharacterRule(EnglishCharacterData.Digit, digitCount),
                new CharacterRule(EnglishCharacterData.Special, specialCount),
                new WhitespaceRule()); // No whitespace;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null)
            return true;

        List<String> errors = new ArrayList<>();
        result = passwordValidator.validate(new PasswordData(password));

        if(!result.isValid()) {
            result.getDetails().forEach(detail -> {
                String errorCode = detail.getErrorCode();

                if (errorCode.equals(LengthRule.ERROR_CODE_MIN) || errorCode.equals(LengthRule.ERROR_CODE_MAX))
                    errors.add(String.format("Password should be more than %d characters long", lenMin));

                else if (errorCode.equals(EnglishCharacterData.Alphabetical.getErrorCode()))
                    errors.add(String.format("Password should at least contain %d alphabet letters", alphabetCount));

                else if (errorCode.equals(EnglishCharacterData.UpperCase.getErrorCode()))
                    errors.add(String.format("Password should at least contain %d uppercase letter", uppercaseCount));

                else if (errorCode.equals(EnglishCharacterData.Digit.getErrorCode()))
                    errors.add(String.format("Password should at least contain %d digit", digitCount));

                else if (errorCode.equals(EnglishCharacterData.Special.getErrorCode()))
                    errors.add(String.format("Password should at least contain %d special character", specialCount));

                else if (errorCode.equals(WhitespaceRule.ERROR_CODE))
                    errors.add("Password should not contain whitespace");
            });
        }

        if(errors.isEmpty())
            return true;

        errors.forEach(error -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
        });
        return false;
    }

}
