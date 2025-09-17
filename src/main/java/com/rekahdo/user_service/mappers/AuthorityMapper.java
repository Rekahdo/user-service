package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.controllers.AppUserController;
import com.rekahdo.user_service.dtos.entities.AuthorityDto;
import com.rekahdo.user_service.entities.Authority;
import com.rekahdo.user_service.enums.AuthorityRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends Api_Mapper<Authority, AuthorityDto> {

	@Override
	@Mapping(target = "role", ignore = true)
	AuthorityDto toDto(Authority authority);

	@Override
	@Mapping(target = "role", ignore = true)
	Authority toEntity(AuthorityDto dto);

	@Override
	@AfterMapping
	default void afterMappingToDto(@MappingTarget AuthorityDto target, Authority source) {
		target.setRole(AuthorityRole.findByIndex(source.getRole()));
		target.add(linkTo(methodOn(AppUserController.class).getUser(source.getAppUser().getId())).withRel("user"));
	}

}
