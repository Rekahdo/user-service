package com.rekahdo.user_service.exceptions.classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyListException extends ResponseStatusException {

	public EmptyListException() {
		super(HttpStatus.NO_CONTENT, "List is empty");
	}

}
