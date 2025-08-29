package com.rekahdo.user_service.populators;

import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.enums.AuthorityRole;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.AuthorityRepository;
import com.rekahdo.user_service.services.AppUserService;
import com.rekahdo.user_service.services.AuthorityService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@DependsOn("appUserPopulator")
@RequiredArgsConstructor
public class AuthorityPopulator {

    private final AuthorityRepository repository;
    private final AuthorityService service;

    @PostConstruct
    private void insertIntoRepository() {
        if (!repository.findAll().isEmpty())
            return;

        service.assignAuthority(1L, new AssignAuthority(AuthorityRole.ADMIN, "pass"));
        service.assignAuthority(2L, new AssignAuthority(AuthorityRole.MODERATOR, "pass"));
        service.assignAuthority(3L, new AssignAuthority(AuthorityRole.EDITOR, "pass"));
    }

}
