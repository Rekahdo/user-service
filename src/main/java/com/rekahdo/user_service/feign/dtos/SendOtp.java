package com.rekahdo.user_service.feign.dtos;

import jakarta.validation.constraints.NotNull;

public record SendOtp(
        @NotNull(message = "'otp' can not be null")
        Integer otp,

        @NotNull(message = "'to' can not be null")
        String to,

        @NotNull(message = "'title' can not be null")
        String title,

        @NotNull(message = "'expireInMinutes' can not be null")
        Integer expireInMinutes) {
}
