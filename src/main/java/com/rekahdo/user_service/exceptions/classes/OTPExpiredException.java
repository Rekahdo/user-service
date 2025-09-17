package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class OTPExpiredException extends ResponseStatusException {

	public OTPExpiredException() {
		super(HttpStatus.EXPECTATION_FAILED, "OTP has expired");
	}

	public OTPExpiredException(Integer otp) {
		super(HttpStatus.NOT_FOUND, String.format("OTP '%d' has expired", otp));
	}


}