package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.constraints.NotNull;

public record EditPhone(
        @CountryCode
        String countryCode,

        @Number
        String number
) implements Dto {}
