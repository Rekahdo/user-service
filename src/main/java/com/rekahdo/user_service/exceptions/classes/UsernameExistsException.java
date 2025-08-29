package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class UsernameExistsException extends ResponseStatusException {

	public UsernameExistsException() {
		super(HttpStatus.FOUND, "Username exists");
	}

	public UsernameExistsException(String name) {
		super(HttpStatus.FOUND, String.format("Username '%s' exists", name));
	}

}