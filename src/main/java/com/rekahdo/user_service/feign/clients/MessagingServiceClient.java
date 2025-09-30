package com.rekahdo.user_service.feign.clients;

import com.rekahdo.user_service.exceptions.fallbacks.MessageServiceFallbackFactory;
import com.rekahdo.user_service.feign.dtos.SendOtp;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "messaging-service",
        url = "${application.messaging-service.url}",
        path = "/api/v1/message/send",
        fallbackFactory = MessageServiceFallbackFactory.class)
public interface MessagingServiceClient {

    @PostMapping(path = "/otp-to-email", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void sendOtpToEmail(@Valid @RequestBody SendOtp sendOtp);

    @PostMapping(path = "/otp-to-number", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void sendOtpToNumber(@Valid @RequestBody SendOtp sendOtp);

}
