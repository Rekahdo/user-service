package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.controllers.AuthorityController;
import com.rekahdo.user_service.controllers.PhoneController;
import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.enums.AuthorityRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends Api_Mapper<AppUser, AppUserDto>{

    @Override
    @AfterMapping
    default void afterMappingToDto(@MappingTarget AppUserDto target, AppUser source) {
        if(source.getAuthority() != null) {
            target.setRole(AuthorityRole.findByIndex(source.getAuthority().getRole()));
            target.add(linkTo(methodOn(AuthorityController.class).getAuthority(source.getId())).withRel("authority"));
        }

        if (source.getPhones() != null)
            target.add(linkTo(methodOn(PhoneController.class).getPhones(source.getId())).withRel("phones"));
    }

}