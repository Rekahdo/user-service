package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.EditUser;
import com.rekahdo.user_service.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface EditUserMapper extends Api_Mapper<AppUser, EditUser>{

    @Override
    default void afterMappingToEntity(@MappingTarget AppUser target, EditUser source) {
        target.setUpdatedAt(LocalDate.now());
    }

}