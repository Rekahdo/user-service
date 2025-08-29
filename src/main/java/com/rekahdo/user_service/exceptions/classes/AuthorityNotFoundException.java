package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AuthorityNotFoundException extends ResponseStatusException {

	public AuthorityNotFoundException() {
		super(HttpStatus.NOT_FOUND, "User has no special role");
	}

	public AuthorityNotFoundException(Long id) {
		super(HttpStatus.NOT_FOUND, String.format("User '%d' has no special role", id));
	}

	public AuthorityNotFoundException(String name) {
		super(HttpStatus.NOT_FOUND, String.format("User '%s' has no special role", name));
	}

}