package com.rekahdo.user_service.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.user_service.enums.OTPPurpose;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonFilter("phoneDtoFilter")
public class OtpDto extends EntityDto<OtpDto> {

	private Integer otp;

	private Long userId;

	private String email;

	private String countryCode;

	private String number;

	private OTPPurpose purpose;

	public String phoneNumber(){
		return String.format("%s%s", countryCode, number);
	}

}