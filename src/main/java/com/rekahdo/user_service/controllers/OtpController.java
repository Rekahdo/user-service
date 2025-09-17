package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.SendOtpToEmail;
import com.rekahdo.user_service.dtos.records.SendOtpToNumber;
import com.rekahdo.user_service.dtos.records.VerifyOtp;
import com.rekahdo.user_service.services.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/otp")
@RequiredArgsConstructor
public class OtpController {

	private final OtpService service;

	@PostMapping(path = "/request/send-to-email", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void sendOtpToEmail(@Valid @RequestBody SendOtpToEmail record) {
		service.sendOtpToEmail(record);
	}

	@PostMapping(path = "/request/send-to-number", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void sendOtpToNumber(@Valid @RequestBody SendOtpToNumber record) {
		service.sendOtpToNumber(record);
	}

	@PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void verifyOtp(@Valid @RequestBody VerifyOtp record) {
		service.verifyOtp(record);
	}

}