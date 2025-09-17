package com.rekahdo.user_service.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.user_service.enums.AuthorityRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonFilter("appUserDtoFilter")
public class AppUserDto extends EntityDto<AppUserDto> {

	private Long id;

	private String username;

	private String email;

	private boolean verified;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private AuthorityRole role;

}