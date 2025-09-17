package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.AuthorityRole;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.constraints.NotNull;

public record AddPhone(
        @NotNull(message = "countryCode can not be null")
        @CountryCode
        String countryCode,

        @NotNull(message = "number can not be null")
        @Number
        String number
) implements Dto {}
