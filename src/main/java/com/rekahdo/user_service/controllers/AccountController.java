package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.*;
import com.rekahdo.user_service.enums.FindBy;
import com.rekahdo.user_service.services.AccountService;
import com.rekahdo.user_service.validations.annotations.CountryCode;
import com.rekahdo.user_service.validations.annotations.Number;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService service;

	@PostMapping(path = "/find-account/by-email")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue findAccountByEmail(@Valid @RequestParam @Email String email) {
		return service.findAccountByEmail(email);
	}

	@PostMapping(path = "/find-account/by-number")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue findAccountNumber(@Valid @RequestParam @CountryCode String countryCode,
												 @Valid @RequestParam @Number String number) {
		return service.findAccountByNumber(countryCode, number);
	}

	@PostMapping(path = "/verify-email", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #record)")
	@ResponseStatus(HttpStatus.OK)
	public void verifyEmail(@Valid @RequestBody VerifyEmail record) {
		service.verifyEmail(record);
	}

	@PostMapping(path = "/verify-number", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@userSecurity.isUserAuth(authentication, #record)")
	@ResponseStatus(HttpStatus.OK)
	public void verifyNumber(@Valid @RequestBody VerifyNumber record) {
		service.verifyNumber(record);
	}

}