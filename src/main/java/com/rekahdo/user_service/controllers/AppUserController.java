package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.paginations.AppUserPageRequestDto;
import com.rekahdo.user_service.dtos.records.CreateUser;
import com.rekahdo.user_service.dtos.records.EditUser;
import com.rekahdo.user_service.dtos.records.JJwtResponse;
import com.rekahdo.user_service.dtos.records.Login;
import com.rekahdo.user_service.services.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class AppUserController {

	private final AppUserService service;

	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody CreateUser record) {
		service.createUser(record);
	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JJwtResponse> login(@RequestBody Login record) {
		return service.login(record);
	}

	@PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId)")
	@ResponseStatus(HttpStatus.OK)
	public void editUser(@PathVariable Long userId, @Valid @RequestBody EditUser record) {
		service.editUser(userId, record);
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('MODERATOR')")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getUsers(@ModelAttribute AppUserPageRequestDto dto) {
		return service.getUsers(dto);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) Or hasRole('ADMIN') OR hasRole('MODERATOR')")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getUser(@PathVariable Long userId) {
		return service.getUser(userId);
	}

	@DeleteMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteUsers(@RequestBody List<Long> userIds) {
		service.deleteUsers(userIds);
	}

	@DeleteMapping(path = "/{userId}")
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable Long userId) {
		service.deleteUser(userId);
	}

	@PostMapping(path = "/test-messaging")
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void testMessaging(@PathVariable Long userId) {
		service.deleteUser(userId);
	}

}