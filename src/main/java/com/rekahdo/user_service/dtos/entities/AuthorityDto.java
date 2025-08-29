package com.rekahdo.user_service.dtos.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.user_service.enums.AuthorityRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonFilter("authorityDtoFilter")
public class AuthorityDto extends EntityDto<AuthorityDto> {

	private byte id;

	private AuthorityRole role;

	private LocalDate assignedAt;

	private String adminKey;

}