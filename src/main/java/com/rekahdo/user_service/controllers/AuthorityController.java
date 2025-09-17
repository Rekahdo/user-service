package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.services.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/authority")
@RequiredArgsConstructor
public class AuthorityController {

	private final AuthorityService service;

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN')")
	public void assignAuthority(@PathVariable Long userId, @Valid @ModelAttribute AssignAuthority record,
								Authentication authentication) {
		service.assignAuthority(userId, record, authentication);
	}

	@GetMapping("")
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	public MappingJacksonValue getAuthority(@PathVariable Long userId) {
		return service.getAuthority(userId);
	}

	@DeleteMapping("")
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN')")
	public void deleteAuthority(@PathVariable Long userId) {
		service.deleteAuthority(userId);
	}

}