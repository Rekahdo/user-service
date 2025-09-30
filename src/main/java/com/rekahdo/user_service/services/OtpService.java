package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.records.SendOtpToEmail;
import com.rekahdo.user_service.dtos.records.SendOtpToNumber;
import com.rekahdo.user_service.dtos.records.VerifyOtp;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Otp;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.exceptions.classes.*;
import com.rekahdo.user_service.feign.clients.MessagingServiceClient;
import com.rekahdo.user_service.feign.dtos.SendOtp;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.OtpRepository;
import com.rekahdo.user_service.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository repository;
    private final AppUserRepository appUserRepository;
    private final PhoneRepository phoneRepository;
    private final MessagingServiceClient messagingServiceClient;
    private final TemplateEngine templateEngine;

    @Value("${otp.expire.minutes}")
    private Integer otpExpireMinutes;

    // Send OTP to user email
    public void sendOtpToEmail(SendOtpToEmail record){
        AppUser user = appUserRepository.findByEmail(record.email())
                .orElseThrow(() -> new AccountNotFoundException(record.email()));

        Integer otp = generate();
        SendOtp sendOtp = new SendOtp(otp, record.email(), record.purpose().getTitle(), otpExpireMinutes);
        messagingServiceClient.sendOtpToEmail(sendOtp);
        saveOtp(otp, user.getId(), record.email(), record.purpose().purpose);
    }

    // Send OTP to user number
    public void sendOtpToNumber(SendOtpToNumber record){
        boolean numberExists = phoneRepository.existsByAppUserIdAndCountryCodeAndNumber(
                record.userId(), record.countryCode(), record.number());

        if(!numberExists) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Phone number '%s' not found for user '%s'", record.phoneNumber(), record.userId()));

        Integer otp = generate();
        SendOtp sendOtp = new SendOtp(otp, record.validPhoneNumber(), record.purpose().getTitle(), otpExpireMinutes);
        messagingServiceClient.sendOtpToNumber(sendOtp);
        saveOtp(otp, record.userId(), record.phoneNumber(), record.purpose().purpose);
    }

    // Save OTP to database
    private void saveOtp(Integer otpCode, Long userId, String sentTo, String purpose){
        Otp otp = repository.findByAppUserId(userId).orElseGet(Otp::new);

        otp.setOtp(otpCode);
        otp.setVerified(false);
        otp.setPurpose(purpose);
        otp.setExpireAt(LocalDateTime.now().plusMinutes(otpExpireMinutes));
        otp.setSentTo(sentTo);
        otp.setAppUser(new AppUser(userId));
        repository.save(otp);
    }

    // Verify user OTP
    public Otp verifyOtp(Integer otpCode, String sentTo, OTPPurpose purpose){
        Otp otp = repository.findByOtp(otpCode)
                .orElseThrow(() -> new OTPNotFoundException(otpCode));

        // Is the retrieved OTP expired
        if(otp.isExpired()){
            repository.delete(otp);
            throw new OTPExpiredException(otp.getOtp());
        }

        if(!Objects.equals(otp.getSentTo(), sentTo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Account '%s' did not request OTP for '%s' purpose", sentTo, purpose));

        if(!Objects.equals(OTPPurpose.findByPurpose(otp.getPurpose()), purpose))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Account '%s' did not request OTP for '%s' purpose", otp.getAppUser().getId(), purpose));

        repository.save(otp);
        return otp;
    }

    // Verify user OTP
    public void verifyOtp(VerifyOtp record){
        Otp otp = repository.findByOtp(record.otp())
                .orElseThrow(() -> new OTPNotFoundException(record.otp()));

        if(otp.isVerified())
            throw new ResponseStatusException(HttpStatus.OK, String.format("OTP '%d' already verified", otp.getOtp()));

        // Is the retrieved OTP expired
        if(otp.isExpired()){
            repository.delete(otp);
            throw new OTPExpiredException(otp.getOtp());
        }

        otp.setVerified(true);
        repository.save(otp);
    }

    public Otp validate(Integer otpCode, OTPPurpose purpose) {
        Otp otp = repository.findByOtp(otpCode)
                .orElseThrow(() -> new OTPNotFoundException(otpCode));

        if(!otp.isVerified())
            throw new OTPVerificationException(otpCode);

        if(!Objects.equals(OTPPurpose.findByPurpose(otp.getPurpose()), purpose))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Account ID '%d' did not request for '%s' OTP", otp.getAppUser().getId(), purpose));

        return otp;
    }

    private Integer generate() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

}
