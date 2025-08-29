package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.dtos.records.EditAccount;
import com.rekahdo.user_service.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface EditAccountMapper extends Api_Mapper<AppUser, EditAccount>{

    @Override
    default void afterMappingToEntity(@MappingTarget AppUser target, EditAccount source) {
        target.setUpdatedAt(LocalDate.now());
    }

}