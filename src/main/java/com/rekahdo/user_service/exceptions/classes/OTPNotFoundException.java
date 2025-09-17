package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class OTPNotFoundException extends ResponseStatusException {

	public OTPNotFoundException() {
		super(HttpStatus.NOT_FOUND, "OTP not found. Place a request for OTP");
	}

	public OTPNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}

	public OTPNotFoundException(Integer otp) {
		super(HttpStatus.NOT_FOUND, String.format("OTP '%d' not found. Place a request for OTP", otp));
	}

}