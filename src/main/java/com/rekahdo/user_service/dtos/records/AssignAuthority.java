package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.AuthorityRole;
import jakarta.validation.constraints.NotNull;

public record AssignAuthority(
        @NotNull(message = "'role' can not be null")
        AuthorityRole role,

        String assignmentKey
) implements Dto {}
