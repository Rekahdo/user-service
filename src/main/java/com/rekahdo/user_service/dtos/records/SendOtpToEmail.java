package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.OTPPurpose;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SendOtpToEmail(
        @NotNull(message = "'email' can not be null")
        @Email
        String email,

        @NotNull(message = "'purpose' can not be null")
        OTPPurpose purpose
) implements Dto {}
