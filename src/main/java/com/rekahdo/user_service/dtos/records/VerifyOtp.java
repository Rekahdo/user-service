package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.OTPPurpose;
import jakarta.validation.constraints.NotNull;

public record VerifyOtp(
        @NotNull(message = "'userId' can not be null")
        Long userId,

        @NotNull(message = "'sentTo' can not be null")
        String sentTo,

        @NotNull(message = "'otp' can not be null")
        Integer otp,

        @NotNull(message = "'purpose' can not be null")
        OTPPurpose purpose
) implements Dto {}
