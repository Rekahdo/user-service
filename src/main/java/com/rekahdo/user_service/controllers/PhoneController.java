package com.rekahdo.user_service.controllers;

import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.dtos.records.EditPhone;
import com.rekahdo.user_service.services.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users/{userId}/phones")
@RequiredArgsConstructor
public class PhoneController {

	private final PhoneService service;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void addPhone(@PathVariable Long userId, @Valid @RequestBody AddPhone record){
		service.addPhone(userId, record);
	}

	@PutMapping("/{phoneId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void editPhone(@PathVariable Long userId, @PathVariable Long phoneId,
								  @Valid @RequestBody EditPhone record){
		service.editPhone(userId, phoneId, record);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getPhones(@PathVariable Long userId){
		return service.getPhones(userId);
	}

	@GetMapping("/{phoneId}")
	@ResponseStatus(HttpStatus.OK)
	public MappingJacksonValue getPhone(@PathVariable Long userId, @PathVariable Long phoneId){
		return service.getPhone(userId, phoneId);
	}

	@DeleteMapping("")
	@ResponseStatus(HttpStatus.OK)
	public void deletePhones(@PathVariable Long userId, @RequestBody List<Long> phoneIds){
		service.deletePhones(userId, phoneIds);
	}

	@DeleteMapping("/{phoneId}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePhone(@PathVariable Long userId, @PathVariable Long phoneId){
		service.deletePhone(userId, phoneId);
	}

}
