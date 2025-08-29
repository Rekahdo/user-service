package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.enums.AuthorityRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends Api_Mapper<AppUser, AppUserDto>{

    @Override
    @AfterMapping
    default void afterMappingToDto(@MappingTarget AppUserDto target, AppUser source) {
        if(source.getAuthority() != null)
            target.setRole(AuthorityRole.findByIndex(source.getAuthority().getRole()));
    }

}