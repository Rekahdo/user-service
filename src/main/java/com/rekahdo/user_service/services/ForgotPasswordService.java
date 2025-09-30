package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.records.ResetPassword;
import com.rekahdo.user_service.entities.Otp;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.mappers.OtpMapper;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final AppUserRepository appUserRepository;

    private final OtpMapper otpMapper;
    private final OtpService otpService;
    private final OtpRepository otpRepository;

    private final PasswordEncoder passwordEncoder;

    public void resetPasswordByEmail(ResetPassword record) {
        if(record.email() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'email' can not be null");

        Otp otp = otpService.validate(record.otp(), OTPPurpose.FORGOT_PASSWORD);

        if(!Objects.equals(otp.getSentTo(), record.email()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("'%s' OTP not found for email '%s'", OTPPurpose.FORGOT_PASSWORD, record.email()));

        resetPassword(otp, record);
    }

    public void resetPasswordByNumber(ResetPassword record) {
        if(record.countryCode() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'countryCode' can not be null");

        if(record.number() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'number' can not be null");

        Otp otp = otpService.validate(record.otp(), OTPPurpose.FORGOT_PASSWORD);

        if(!Objects.equals(otp.getSentTo(), record.phoneNumber()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("'%s' OTP not found for phone number '%s'", OTPPurpose.FORGOT_PASSWORD, record.phoneNumber()));

        resetPassword(otp, record);
    }

    private void resetPassword(Otp otp, ResetPassword record) {
        if(!Objects.equals(record.password(), record.repeatPassword()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "'repeatPassword' does not match 'password'");

        String encodedPassword = passwordEncoder.encode(record.repeatPassword());
        appUserRepository.updatePassword(encodedPassword, otp.getAppUser().getId());
        otpRepository.deleteByOtp(otp.getOtp());
    }

}
