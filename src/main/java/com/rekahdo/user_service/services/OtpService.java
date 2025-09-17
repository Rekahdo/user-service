package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.entities.OtpDto;
import com.rekahdo.user_service.dtos.records.SendOtpToEmail;
import com.rekahdo.user_service.dtos.records.SendOtpToNumber;
import com.rekahdo.user_service.dtos.records.VerifyOtp;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Otp;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.exceptions.classes.OTPExpiredException;
import com.rekahdo.user_service.exceptions.classes.OTPNotFoundException;
import com.rekahdo.user_service.exceptions.classes.OTPVerificationException;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.OtpRepository;
import com.rekahdo.user_service.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository repository;
    private final AppUserRepository appUserRepository;
    private final PhoneRepository phoneRepository;
    private final MessagingService messagingService;

    @Value("${otp.expire.seconds}")
    private Integer otpExpireSeconds;

    @Value("${otp.expire.minutes}")
    private Integer otpExpireMinutes;

    // Send OTP to user email
    public void sendOtpToEmail(SendOtpToEmail record){
        AppUser user = appUserRepository.findById(record.userId())
                .orElseThrow(() -> new UserNotFoundException(record.userId()));

        if(!Objects.equals(user.getEmail(), record.email()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Email '%s' not found for user '%s'", record.email(), record.userId()));

        Integer otp = generate();
        messagingService.sendToEmail(record.email(), record.purpose().title, otp.toString());
        saveOtp(otp, record.userId(), record.email(), record.purpose().purpose);
    }

    // Send OTP to user number
    public void sendOtpToNumber(SendOtpToNumber record){
        AppUser user = appUserRepository.findById(record.userId())
                .orElseThrow(() -> new UserNotFoundException(record.userId()));

        user.getPhones().stream()
                .filter(phone -> phone.phoneNumber().equals(record.phoneNumber()))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Phone number '%s' not found for user '%s'", record.phoneNumber(), record.userId())));

        Integer otp = generate();
        messagingService.sendToPhoneNumber(record.phoneNumber(), record.purpose().title, otp.toString());
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
    public void verifyOtp(VerifyOtp record){
        Otp otp = repository.findByOtp(record.otp())
                .orElseThrow(() -> new OTPNotFoundException(record.otp()));

        // Does the record user own the retrieved OTP's user
        if(!Objects.equals(record.userId(), otp.getAppUser().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("OTP '%d' not found for user '%d'", record.otp(), record.userId()));

        // Does the record otp-purpose match the retrieved OTP's otp-purpose
        if(!Objects.equals(record.purpose().getPurpose(), otp.getPurpose()))
            throw new OTPNotFoundException(String.format("OTP '%d' not found for '%s' purpose", record.otp(),
                    OTPPurpose.findByPurpose(record.purpose().getPurpose())));

        // Does the record sentTo match the retrieved OTP's sentTo
        if(!Objects.equals(record.sentTo(), otp.getSentTo()))
            throw new OTPNotFoundException(String.format("OTP '%d' not found for '%s'", record.otp(), record.sentTo()));

        // Is the retrieved OTP expired
        if(LocalDateTime.now().isAfter(otp.getExpireAt())){
            repository.delete(otp);
            throw new OTPExpiredException(otp.getOtp());
        }

        if(otp.isVerified())
            throw new ResponseStatusException(HttpStatus.OK, String.format("OTP '%d' already verified", otp.getOtp()));

        otp.setVerified(true);
        repository.save(otp);
    }

    public Otp validate(Integer otpCode, OTPPurpose purpose) {
        Otp otp = repository.findByOtp(otpCode)
                .orElseThrow(() -> new OTPNotFoundException(otpCode));

        if(!otp.isVerified())
            throw new OTPVerificationException(otpCode);

        if(!Objects.equals(OTPPurpose.findByPurpose(otp.getPurpose()), purpose))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User '%d' did not request OTP for '%s' purpose",
                    otp.getAppUser().getId(), purpose));

        return otp;
    }

    private Integer generate() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

}
