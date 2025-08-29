package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.records.EditPhone;
import com.rekahdo.user_service.entities.Phone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditPhoneMapper extends Api_Mapper<Phone, EditPhone>{

}