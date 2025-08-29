package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Phone;
import com.rekahdo.user_service.enums.AuthorityRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddPhoneMapper extends Api_Mapper<Phone, AddPhone>{

}