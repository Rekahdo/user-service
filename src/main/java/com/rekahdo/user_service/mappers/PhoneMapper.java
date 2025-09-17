package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.controllers.AppUserController;
import com.rekahdo.user_service.dtos.entities.PhoneDto;
import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.entities.Phone;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface PhoneMapper extends Api_Mapper<Phone, PhoneDto>{

    @Override
    @AfterMapping
    default void afterMappingToDto(@MappingTarget PhoneDto target, Phone source) {
        String number = source.getNumber().startsWith("0")
                ? source.getNumber().substring(1, 11) : source.getNumber();

        target.setPhoneNumber(String.format("+%s%s", source.getCountryCode(), number));
        target.add(linkTo(methodOn(AppUserController.class).getUser(source.getAppUser().getId())).withRel("user"));
    }
}