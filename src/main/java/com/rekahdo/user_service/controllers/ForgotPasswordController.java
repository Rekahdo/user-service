package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.FindAccount;
import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.dtos.records.SendOtp;
import com.rekahdo.user_service.dtos.records.VerifyOtp;
import com.rekahdo.user_service.enums.SendBy;
import com.rekahdo.user_service.services.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/forgot/password")
@RequiredArgsConstructor
public class ForgotPasswordController {

	private final ForgotPasswordService service;

	@PostMapping(path = "/find-account", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue findAccount(@Valid @RequestBody FindAccount record) {
		return service.findAccount(record);
	}

	@PostMapping(path = "/send-otp", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sendOTP(@Valid @RequestBody SendOtp record) {
		service.sendOTP(record);
	}

	@PostMapping(path = "/verify-otp", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void verifyOTP(@Valid @RequestBody VerifyOtp record) {
		service.verifyOTP(record);
	}

	@PostMapping(path = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void resetPassword(@Valid @RequestBody ResetPassword record) {
		service.resetPassword(record);
	}

}