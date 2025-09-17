package com.rekahdo.user_service.validations.validators;

import com.rekahdo.user_service.validations.annotations.Username;
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
public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMin = 2;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMax = 20;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int alphabetCount = 2;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int digitCount = 0;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final String regex = String.format("^[a-zA-Z].{%d,}", alphabetCount-1);

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final PasswordValidator usernameValidator;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private RuleResult result;

    public UsernameValidator() {
        usernameValidator = new PasswordValidator(
                new LengthRule(lenMin, lenMax),
                new CharacterRule(EnglishCharacterData.Alphabetical, alphabetCount),
                new AllowedRegexRule(regex),
                new WhitespaceRule()
        );
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(username == null)
            return true;

        List<String> errors = new ArrayList<>();
        result = usernameValidator.validate(new PasswordData(username));

        if(!result.isValid()) {
            result.getDetails().forEach(detail -> {
                String errorCode = detail.getErrorCode();

                if(errorCode.equals(LengthRule.ERROR_CODE_MIN) || errorCode.equals(LengthRule.ERROR_CODE_MAX))
                    errors.add(String.format("Username should be between %d and %d characters long", lenMin, lenMax));

//                else if (errorCode.equals(EnglishCharacterData.Alphabetical.getErrorCode()))
//                    errors.add(String.format("Username should contain %d alphabet letters", alphabetCount));

                else if (errorCode.equals(AllowedRegexRule.ERROR_CODE))
                    errors.add(String.format("Username should start with %d or more alphabet letters", alphabetCount));

                else if (errorCode.equals(WhitespaceRule.ERROR_CODE))
                    errors.add("Username should not contain whitespace");
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
