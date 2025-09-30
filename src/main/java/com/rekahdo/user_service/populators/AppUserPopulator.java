package com.rekahdo.user_service.populators;

import com.rekahdo.user_service.dtos.records.CreateUser;
import com.rekahdo.user_service.dtos.records.EditUser;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.services.AppUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class AppUserPopulator {

    private final AppUserRepository repository;
    private final AppUserService service;

    @PostConstruct
    private void insert() {
        if (!repository.findAll().isEmpty())
            return;

        service.createUser(new CreateUser("rekahdo", "Passcode@1", "okaforrichard76@gmail.com"));
        service.createUser(new CreateUser("john", "Passcode@1", "johnokafor@gmail.com"));
        service.createUser(new CreateUser("mary", "Passcode@1", "marynwankwo@gmail.com"));
        service.createUser(new CreateUser("peter", "Passcode@1", "peterparker@gmail.com"));
        service.createUser(new CreateUser("james", "Passcode@1", "jamesgaun@gmail.com"));

        repository.verifyAccount(1L);
        repository.verifyAccount(2L);
        repository.verifyAccount(3L);
        repository.verifyAccount(4L);
        repository.verifyAccount(5L);
    }

}
