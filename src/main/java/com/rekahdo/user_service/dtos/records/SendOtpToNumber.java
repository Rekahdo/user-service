package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.entities.Otp;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.constraints.NotNull;

public record SendOtpToNumber(
        @NotNull(message = "'userId' can not be null")
        Long userId,

        @NotNull(message = "'countryCode' can not be null")
        @CountryCode
        String countryCode,

        @NotNull(message = "'number' can not be null")
        @Number
        String number,

        @NotNull(message = "'purpose' can not be null")
        OTPPurpose purpose
) implements Dto {

        public String phoneNumber() {
                return String.format("%s%s", countryCode, number);
        }

        public String validPhoneNumber() {
                return String.format("+%s%s", countryCode, number);
        }

}
