package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.records.FindAccount;
import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.dtos.records.SendOtp;
import com.rekahdo.user_service.dtos.records.VerifyOtp;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.enums.FindBy;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.mappers.AppUserMapper;
import com.rekahdo.user_service.mappingJV.FindAccountMJV;
import com.rekahdo.user_service.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    private final FindAccountMJV findAccountMJV;

    public MappingJacksonValue findAccount(FindAccount record) {
        List<AppUser> appUsers = record.findBy().equals(FindBy.EMAIL)
                ? appUserRepository.findByEmail(record.value())
                : appUserRepository.findByPhoneNumber(record.value());
        if(appUsers.isEmpty() && record.findBy().equals(FindBy.EMAIL))
            throw new UserNotFoundException("email", record.value());
        else if(appUsers.isEmpty() && record.findBy().equals(FindBy.PHONE))
            throw new UserNotFoundException("phone", record.value());

        List<AppUserDto> appUserDtos = appUserMapper.toDtoList(appUsers);
        return findAccountMJV.listFilter(appUserDtos);
    }

    public void sendOTP(SendOtp record) {
    }

    public void verifyOTP(VerifyOtp record) {
    }

    public void resetPassword(ResetPassword record) {
    }
}
