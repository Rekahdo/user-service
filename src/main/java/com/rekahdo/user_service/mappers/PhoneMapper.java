package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.entities.PhoneDto;
import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.entities.Phone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneMapper extends Api_Mapper<Phone, PhoneDto>{

    @Override
    default void afterMappingToDto(PhoneDto target, Phone source) {
        String number = source.getNumber().startsWith("0")
                ? source.getNumber().substring(1, 11) : source.getNumber();
        target.setPhoneNumber(source.getCountryCode().concat(number));
    }
}