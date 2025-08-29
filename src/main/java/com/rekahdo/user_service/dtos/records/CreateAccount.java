package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import jakarta.validation.constraints.NotNull;

public record CreateAccount(
        @NotNull(message = " can not be null")
        String username,

        @NotNull(message = " can not be null")
        String password
) implements Dto {}
