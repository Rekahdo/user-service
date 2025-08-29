package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class PhoneNotFoundException extends ResponseStatusException {

	public PhoneNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Phone not found");
	}

	public PhoneNotFoundException(Long id) {
		super(HttpStatus.NOT_FOUND, String.format("Phone '%d' not found", id));
	}

	public PhoneNotFoundException(String name) {
		super(HttpStatus.NOT_FOUND, String.format("Phone '%s' not found", name));
	}

}