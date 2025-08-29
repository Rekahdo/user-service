package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.SendBy;
import jakarta.validation.constraints.NotNull;

public record ResetPassword(
        @NotNull(message = "userId can not be null") Long userId,
        @NotNull(message = "password can not be null") String password,
        @NotNull(message = "samePassword can not be null") String samePassword
) implements Dto {}
