package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.validations.annotations.StrongPassword;
import com.rekahdo.user_service.validations.annotations.Username;
import jakarta.validation.constraints.NotNull;

public record CreateUser(
        @NotNull(message = "username can not be null")
        @Username
        String username,

        @NotNull(message = "password can not be null")
        @StrongPassword
        String password
) implements Dto {}
