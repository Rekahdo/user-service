package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.entities.OtpDto;
import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.dtos.records.VerifyEmail;
import com.rekahdo.user_service.dtos.records.VerifyNumber;
import com.rekahdo.user_service.enums.OTPPurpose;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OtpMapper {

    OtpDto fromVerifyEmail(VerifyEmail verifyEmail);
    OtpDto fromVerifyNumber(VerifyNumber verifyNumber);
    OtpDto fromResetPassword(ResetPassword resetPassword);
}