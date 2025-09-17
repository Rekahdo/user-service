package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record VerifyNumber(
        @NotNull(message = "'otp' can not be null")
        Integer otp,

        @NotNull(message = "'countryCode' can not be null")
        @CountryCode
        String countryCode,

        @NotNull(message = "'number' can not be null")
        @Number
        String number
) implements Dto {

        public String phoneNumber() {
                return String.format("%s%s", countryCode, number);
        }

}
