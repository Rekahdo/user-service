package com.rekahdo.user_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class MessagingService {

    public void sendToEmail(String toEmail, String title, String content) {
    }

    public void sendToPhoneNumber(String toPhoneNumber, String title, String content) {
    }

}