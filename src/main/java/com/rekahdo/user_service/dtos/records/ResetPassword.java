package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.validations.annotations.StrongPassword;
import jakarta.validation.constraints.NotNull;

public record ResetPassword(
        @NotNull(message = "otp can not be null")
        Integer otp,

        @NotNull(message = "password can not be null")
        @StrongPassword
        String password,

        @NotNull(message = "repeatPassword can not be null")
        String repeatPassword
) implements Dto {}
