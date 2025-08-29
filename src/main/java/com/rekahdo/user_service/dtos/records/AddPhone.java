package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.AuthorityRole;
import jakarta.validation.constraints.NotNull;

public record AddPhone(
        @NotNull(message = "countryCode can not be null")
        String countryCode,

        @NotNull(message = "number can not be null")
        String number
) implements Dto {}
