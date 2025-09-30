package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.validations.annotations.StrongPassword;
import com.rekahdo.user_service.validations.annotations.Username;
import jakarta.validation.constraints.Email;

public record EditUser(

        @Username
        String username,

        @StrongPassword
        String password,

        @Email
        String email
) implements Dto {}
