package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.OTPPurpose;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record VerifyEmail(
        @NotNull(message = "'otp' can not be null")
        Integer otp,

        @NotNull(message = "'email' can not be null")
        @Email
        String email
) implements Dto {}
