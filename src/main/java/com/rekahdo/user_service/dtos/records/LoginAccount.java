package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import jakarta.validation.constraints.NotNull;

public record LoginAccount(
        @NotNull(message = "username can not be null") String username,
        @NotNull(message = "password can not be null") String password
) implements Dto {}
