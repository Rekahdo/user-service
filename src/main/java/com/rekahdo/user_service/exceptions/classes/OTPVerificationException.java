package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class OTPVerificationException extends ResponseStatusException {

	public OTPVerificationException() {
		super(HttpStatus.EXPECTATION_FAILED, "OTP has not been verified");
	}

	public OTPVerificationException(Integer otp) {
		super(HttpStatus.EXPECTATION_FAILED, String.format("OTP '%d' has not been verified", otp));
	}

}