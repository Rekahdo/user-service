package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.paginations.AppUserPageRequestDto;
import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.dtos.records.EditAccount;
import com.rekahdo.user_service.dtos.records.LoginAccount;
import com.rekahdo.user_service.services.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class AppUserController {

	private final AppUserService service;

	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createAccount(@Valid @RequestBody CreateAccount record) {
		service.createAccount(record);
	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void loginAccount(@RequestBody LoginAccount record) {
		service.loginAccount(record);
	}

	@PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void editAccount(@PathVariable Long userId, @Valid @RequestBody EditAccount record) {
		service.editAccount(userId, record);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getAccounts(@ModelAttribute AppUserPageRequestDto dto) {
		return service.getAccounts(dto);
	}

	@GetMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getAccount(@PathVariable Long userId) {
		return service.getAccount(userId);
	}

	@DeleteMapping("")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccounts(@RequestBody List<Long> userIds) {
		service.deleteAccounts(userIds);
	}

	@DeleteMapping(path = "/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccount(@PathVariable Long userId) {
		service.deleteAccount(userId);
	}

}