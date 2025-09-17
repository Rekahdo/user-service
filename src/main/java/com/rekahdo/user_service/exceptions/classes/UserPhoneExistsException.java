package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class UserPhoneExistsException extends ResponseStatusException {

	public UserPhoneExistsException() {
		super(HttpStatus.FOUND, "User Phone exists");
	}

	public UserPhoneExistsException(String countryCode, String number) {
		super(HttpStatus.FOUND, String.format("User Phone '%s%s' exists", countryCode, number));
	}

}