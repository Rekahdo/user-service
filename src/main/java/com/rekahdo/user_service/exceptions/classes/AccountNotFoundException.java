package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AccountNotFoundException extends ResponseStatusException {

	public AccountNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Account not found");
	}

	public AccountNotFoundException(String email) {
		super(HttpStatus.NOT_FOUND, String.format("Account '%s' not found", email));
	}

}