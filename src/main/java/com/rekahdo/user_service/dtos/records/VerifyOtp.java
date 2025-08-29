package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.SendBy;
import jakarta.validation.constraints.NotNull;

public record VerifyOtp(
        @NotNull(message = "userId can not be null") Long userId,
        @NotNull(message = "contact can not be null") String contact,
        @NotNull(message = "otp can not be null") String otp
) implements Dto {}
