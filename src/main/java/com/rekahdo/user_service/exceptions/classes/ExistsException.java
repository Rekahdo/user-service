package com.rekahdo.user_service.exceptions.classes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ExistsException extends ResponseStatusException {

	public ExistsException() {
		super(HttpStatus.FOUND, "Data exists");
	}

	public ExistsException(Long id) {
		super(HttpStatus.FOUND, String.format("Data '%d' exists", id));
	}

	public ExistsException(String name) {
		super(HttpStatus.FOUND, String.format("Data '%s' exists", name));
	}

}