package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.services.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/authority")
@RequiredArgsConstructor
public class AuthorityController {

	private final AuthorityService service;

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void assignAuthority(@PathVariable Long userId, @Valid @RequestBody AssignAuthority record) {
		service.assignAuthority(userId, record);
	}

	@GetMapping("")
	public MappingJacksonValue getAuthority(@PathVariable Long userId) {
		return service.getAuthority(userId);
	}

	@DeleteMapping("")
	public void deleteAuthority(@PathVariable Long userId) {
		service.deleteAuthority(userId);
	}

}