package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.entities.Authority;
import com.rekahdo.user_service.enums.AuthorityRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssignAuthorityMapper extends Api_Mapper<Authority, AssignAuthority> {

	@Override
	@AfterMapping
	default void afterMappingToEntity(@MappingTarget Authority target, AssignAuthority source) {
		target.setRole(source.role().getIndex());
	}
}
