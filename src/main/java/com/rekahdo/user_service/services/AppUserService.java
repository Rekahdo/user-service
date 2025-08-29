package com.rekahdo.user_service.services;

import com.rekahdo.user_service.controllers.AppUserController;
import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.paginations.AppUserPageRequestDto;
import com.rekahdo.user_service.dtos.records.CreateAccount;
import com.rekahdo.user_service.dtos.records.EditAccount;
import com.rekahdo.user_service.dtos.records.LoginAccount;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.exceptions.classes.UsernameExistsException;
import com.rekahdo.user_service.mappers.AppUserMapper;
import com.rekahdo.user_service.mappers.CreateAccountMapper;
import com.rekahdo.user_service.mappers.EditAccountMapper;
import com.rekahdo.user_service.mappingJV.AppUserMJV;
import com.rekahdo.user_service.repositories.AppUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final AppUserMJV mjv;

    private final CreateAccountMapper createAccountMapper;
    private final EditAccountMapper editAccountMapper;

    private final PasswordEncoder passwordEncoder;

    public void createAccount(CreateAccount record) {
        if(repository.existsByUsername(record.username()))
            throw new UsernameExistsException(record.username());

        AppUser user = createAccountMapper.toEntity(record);
        user.setPassword(passwordEncoder.encode(record.password()));
        repository.save(user);
    }

    public void loginAccount(LoginAccount record) {

    }

    public void editAccount(Long userId, EditAccount record) {
        AppUser appUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        editAccountMapper.updateEntity(record, appUser);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        repository.save(appUser);
    }

    public MappingJacksonValue getAccounts(AppUserPageRequestDto dto) {
        Page<AppUser> appUserPage = repository.findAll(dto.getPageable());

        Page<AppUserDto> appUserDtoPage = appUserPage.map(mapper::toDto);
        PagedModel<AppUserDto> pagedModel = dto.getPagedModel(appUserDtoPage, methodOn(AppUserController.class).getAccounts(null));
        return mjv.listFilter(pagedModel);
    }

    public MappingJacksonValue getAccount(Long userId) {
        AppUser appUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        AppUserDto dto = mapper.toDto(appUser);
        return mjv.selfFilter(dto);
    }

    public void deleteAccounts(List<Long> userIds) {
        repository.deleteAllById(userIds);
    }

    public void deleteAccount(Long userId) {
        repository.deleteById(userId);
    }

}
