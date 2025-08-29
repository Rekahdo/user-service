package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface CreateAccountMapper extends Api_Mapper<AppUser, CreateAccount>{

    @Override
    @AfterMapping
    default void afterMappingToEntity(@MappingTarget AppUser target, CreateAccount source) {
        target.setCreatedAt(LocalDate.now());
    }

}