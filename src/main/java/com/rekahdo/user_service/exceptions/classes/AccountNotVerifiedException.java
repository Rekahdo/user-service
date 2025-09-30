package com.rekahdo.user_service.exceptions.classes;

import com.rekahdo.user_service.enums.OTPPurpose;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AccountNotVerifiedException extends ResponseStatusException {

	public AccountNotVerifiedException() {
		super(HttpStatus.NOT_FOUND,String.format("Account exists but not verified. " +
				"Request OTP for '%s' purpose to verify account", OTPPurpose.ACCOUNT_VERIFICATION));
	}

	public AccountNotVerifiedException(String email) {
		super(HttpStatus.NOT_FOUND, String.format("Account with email '%s' exists but not verified. " +
				"Request OTP for '%s' purpose to verify account", email, OTPPurpose.ACCOUNT_VERIFICATION));
	}

}