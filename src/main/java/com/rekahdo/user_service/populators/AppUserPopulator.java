package com.rekahdo.user_service.populators;

import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.dtos.records.EditAccount;
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
    private void insertIntoRepository() {
        if (!repository.findAll().isEmpty())
            return;

        service.createAccount(new CreateAccount("rekahdo", "pass"));
        service.createAccount(new CreateAccount("john", "pass"));
        service.createAccount(new CreateAccount("mary", "pass"));
        service.createAccount(new CreateAccount("peter", "pass"));
        service.createAccount(new CreateAccount("james", "pass"));

        service.editAccount(1L, new EditAccount(null, null, "okaforrichard76@gmail.com"));
        service.editAccount(2L, new EditAccount(null, null, "johnokafor@gmail.com"));
        service.editAccount(3L, new EditAccount(null, null, "marynwankwo@gmail.com"));
    }

}
