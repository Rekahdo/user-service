package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.OTPPurpose;
import jakarta.validation.constraints.NotNull;

public record VerifyOtp(

        @NotNull(message = "'otp' can not be null")
        Integer otp

) implements Dto {}
