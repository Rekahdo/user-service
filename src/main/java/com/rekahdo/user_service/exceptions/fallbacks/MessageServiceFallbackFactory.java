package com.rekahdo.user_service.exceptions.fallbacks;

import com.rekahdo.user_service.feign.clients.MessagingServiceClient;
import com.rekahdo.user_service.feign.dtos.SendOtp;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceFallbackFactory implements FallbackFactory<MessagingServiceClient> {
    @Override
    public MessagingServiceClient create(Throwable cause) {

        return new MessagingServiceClient() {
            @Override
            public void sendOtpToEmail(SendOtp sendOtp) {
                // Add fallback logic
                System.out.printf("Failed to send OTP to '%s'\nCAUSE: %s", sendOtp.to(), cause);
            }
            
            @Override
            public void sendOtpToNumber(SendOtp sendOtp) {
                // Add fallback logic
                System.out.printf("Failed to send OTP to '%s'\nCAUSE: %s", sendOtp.to(), cause);
            }
        };

    }
}