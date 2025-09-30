package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import com.rekahdo.user_service.validations.annotations.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ResetPassword(
        @NotNull(message = "'otp' can not be null")
        Integer otp,

        @Email
        String email,

        @CountryCode
        String countryCode,

        @Number
        String number,

        @NotNull(message = "'password' can not be null")
        @StrongPassword
        String password,

        @NotNull(message = "'repeatPassword' can not be null")
        String repeatPassword
) implements Dto {

        public String phoneNumber() {
                return String.format("%s%s", countryCode, number);
        }

}
