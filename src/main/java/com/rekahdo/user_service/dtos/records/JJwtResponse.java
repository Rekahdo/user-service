package com.rekahdo.user_service.dtos.records;

import jakarta.validation.constraints.NotNull;

public record JJwtResponse(@NotNull String token, String secretKey) {}