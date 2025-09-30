package com.rekahdo.user_service.mappers;

import com.rekahdo.user_service.dtos.entities.OtpDto;
import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.dtos.records.VerifyAccount;
import com.rekahdo.user_service.dtos.records.VerifyNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {

    OtpDto fromVerifyEmail(VerifyAccount verifyAccount);
    OtpDto fromVerifyNumber(VerifyNumber verifyNumber);
    OtpDto fromResetPassword(ResetPassword resetPassword);
}