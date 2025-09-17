package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.records.VerifyEmail;
import com.rekahdo.user_service.dtos.records.VerifyNumber;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Otp;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.mappers.AppUserMapper;
import com.rekahdo.user_service.mappers.OtpMapper;
import com.rekahdo.user_service.mappingJV.FindAccountMJV;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.OtpRepository;
import com.rekahdo.user_service.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final OtpService otpService;
    private final OtpRepository otpRepository;
    private final AppUserRepository appUserRepository;
    private final PhoneRepository phoneRepository;

    private final OtpMapper otpMapper;
    private final FindAccountMJV findAccountMJV;
    private final AppUserMapper appUserMapper;

    public MappingJacksonValue findAccountByEmail(String email) {
        List<AppUser> appUsers = appUserRepository.findByEmail(email);
        if(appUsers.isEmpty())
            throw new UserNotFoundException("email", email);

        List<AppUserDto> appUserDtos = appUserMapper.toDtoList(appUsers);
        return findAccountMJV.listFilter(appUserDtos);
    }

    public MappingJacksonValue findAccountByNumber(String countryCode, String number) {
        List<AppUser> appUsers = appUserRepository.findAllByPhones_CountryCodeAndPhones_Number(countryCode, number);

        if(appUsers.isEmpty())
            throw new UserNotFoundException("phone", String.format("%s%s", countryCode, number));

        List<AppUserDto> appUserDtos = appUserMapper.toDtoList(appUsers);
        return findAccountMJV.listFilter(appUserDtos);
    }

    public void verifyEmail(VerifyEmail record) {
        Otp otp = otpService.validate(record.otp(), OTPPurpose.EMAIL_VERIFICATION);
        appUserRepository.verifyUserEmail(otp.getAppUser().getId());
        otpRepository.deleteByOtp(otp.getOtp());
    }

    public void verifyNumber(VerifyNumber record) {
        Otp otp = otpService.validate(record.otp(), OTPPurpose.NUMBER_VERIFICATION);
        phoneRepository.verifyUserPhoneNumber(otp.getAppUser().getId(), otp.getSentTo());
        otpRepository.deleteByOtp(otp.getOtp());
    }

}
