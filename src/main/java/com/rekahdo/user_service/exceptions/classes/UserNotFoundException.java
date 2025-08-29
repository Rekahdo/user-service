package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class UserNotFoundException extends ResponseStatusException {

	public UserNotFoundException() {
		super(HttpStatus.NOT_FOUND, "User not found");
	}

	public UserNotFoundException(Long id) {
		super(HttpStatus.NOT_FOUND, String.format("User '%d' not found", id));
	}

	public UserNotFoundException(String name) {
		super(HttpStatus.NOT_FOUND, String.format("User '%s' not found", name));
	}

	public UserNotFoundException(String value, String name) {
		super(HttpStatus.NOT_FOUND, String.format("User %s '%s' not found", value, name));
	}

}