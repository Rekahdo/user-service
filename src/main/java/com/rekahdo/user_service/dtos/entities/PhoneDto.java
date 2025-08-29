package com.rekahdo.user_service.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonFilter("phoneDtoFilter")
public class PhoneDto extends EntityDto<PhoneDto> {

	private Long id;

	@NotNull(message = "countryCode can not be null")
	private String countryCode;

	@NotNull(message = "number can not be null")
	private String number;

	private boolean verified;

	private String phoneNumber;

}