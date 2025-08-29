package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;

public record EditAccount(
        String username,
        String password,
        String email
) implements Dto {}
