package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import jakarta.validation.constraints.NotNull;

public record EditPhone(
        @NotNull(message = "countryCode can not be null")
        String countryCode,

        @NotNull(message = "number can not be null")
        String number
) implements Dto {}
