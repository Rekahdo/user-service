package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.entities.Phone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddPhoneMapper extends Api_Mapper<Phone, AddPhone>{

}