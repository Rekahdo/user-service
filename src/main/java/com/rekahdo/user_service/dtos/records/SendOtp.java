package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.FindBy;
import com.rekahdo.user_service.enums.SendBy;
import jakarta.validation.constraints.NotNull;

public record SendOtp(
        @NotNull(message = "userId can not be null") Long userId,
        @NotNull(message = "sendBy can not be null") SendBy sendBy,
        @NotNull(message = "value can not be null") String value
) implements Dto {}
