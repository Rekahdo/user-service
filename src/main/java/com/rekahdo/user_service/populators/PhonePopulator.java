package com.rekahdo.user_service.populators;

import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.dtos.records.EditPhone;
import com.rekahdo.user_service.enums.AuthorityRole;
import com.rekahdo.user_service.repositories.AuthorityRepository;
import com.rekahdo.user_service.repositories.PhoneRepository;
import com.rekahdo.user_service.services.AuthorityService;
import com.rekahdo.user_service.services.PhoneService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@DependsOn("appUserPopulator")
@RequiredArgsConstructor
public class PhonePopulator {

    private final PhoneRepository repository;
    private final PhoneService service;

    @PostConstruct
    private void insertIntoRepository() {
        if (!repository.findAll().isEmpty())
            return;

        service.addPhone(1L, new AddPhone("235", "9097114625"));
        service.addPhone(2L, new AddPhone("234", "9097114626"));
        service.addPhone(2L, new AddPhone("234", "9030340615"));
        service.addPhone(3L, new AddPhone("1", "8045649213"));
        service.addPhone(3L, new AddPhone("234", "9030340615"));

        service.editPhone(1L, 1L, new EditPhone(null, "9097114626"));
        service.editPhone(1L, 1L, new EditPhone("234", null));
    }

}
