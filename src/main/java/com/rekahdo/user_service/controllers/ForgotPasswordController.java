package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.services.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

	private final ForgotPasswordService service;

	@PostMapping(path = "/reset-password/by-email", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void resetPasswordByEmail(@Valid @RequestBody ResetPassword record) {
		service.resetPasswordByEmail(record);
	}

	@PostMapping(path = "/reset-password/by-number", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void resetPasswordByNumber(@Valid @RequestBody ResetPassword record) {
		service.resetPasswordByNumber(record);
	}

}