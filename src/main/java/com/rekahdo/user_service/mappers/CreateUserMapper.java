package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.CreateUser;
import com.rekahdo.user_service.entities.AppUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface CreateUserMapper extends Api_Mapper<AppUser, CreateUser>{

    @Override
    @AfterMapping
    default void afterMappingToEntity(@MappingTarget AppUser target, CreateUser source) {
        target.setCreatedAt(LocalDate.now());
    }

}