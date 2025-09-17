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

    public void resetPassword(ResetPassword record) {
        Otp otp = otpService.validate(record.otp(), OTPPurpose.FORGOT_PASSWORD);

        if(!Objects.equals(record.password(), record.repeatPassword()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "'repeatPassword' does not match 'password'");

        String encodedPassword = passwordEncoder.encode(record.repeatPassword());
        appUserRepository.updatePassword(encodedPassword, otp.getAppUser().getId());
        otpRepository.deleteByOtp(otp.getOtp());
    }

}
