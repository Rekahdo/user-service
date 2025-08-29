package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.AuthorityRole;
import jakarta.validation.constraints.NotNull;

public record AssignAuthority(
        @NotNull(message = "role can not be null") AuthorityRole role,
        @NotNull(message = "adminKey can not be null") String adminKey
) implements Dto {}
