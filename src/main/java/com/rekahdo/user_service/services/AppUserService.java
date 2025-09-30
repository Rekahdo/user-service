package com.rekahdo.user_service.services;

import com.rekahdo.user_service.controllers.AppUserController;
import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.dtos.paginations.AppUserPageRequestDto;
import com.rekahdo.user_service.dtos.records.CreateUser;
import com.rekahdo.user_service.dtos.records.EditUser;
import com.rekahdo.user_service.dtos.records.JJwtResponse;
import com.rekahdo.user_service.dtos.records.Login;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.exceptions.classes.AccountExistsException;
import com.rekahdo.user_service.exceptions.classes.AccountNotVerifiedException;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.exceptions.classes.UsernameExistsException;
import com.rekahdo.user_service.mappers.AppUserMapper;
import com.rekahdo.user_service.mappers.CreateUserMapper;
import com.rekahdo.user_service.mappers.EditUserMapper;
import com.rekahdo.user_service.mappingJV.AppUserMJV;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.PhoneRepository;
import com.rekahdo.user_service.security.jjwt.JwtSymmetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final AppUserMJV mjv;

    private final CreateUserMapper createUserMapper;
    private final EditUserMapper editUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final PhoneRepository phoneRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtSymmetricService jwtService;

    public void createUser(CreateUser record) {
        String username = record.username(), email = record.email();
        List<AppUser> appUsers = repository.findByUsernameIgnoreCaseOrEmailIgnoreCase(username, email);
        appUsers.forEach(appUser -> {
            if(Objects.equals(email, appUser.getEmail()) && appUser.isVerified())
                throw new AccountExistsException(email);

            if(Objects.equals(email, appUser.getEmail()))
                throw new AccountNotVerifiedException(email);

            if(Objects.equals(username, appUser.getEmail()))
                throw new UsernameExistsException(record.username());
        });

        AppUser user = createUserMapper.toEntity(record);
        user.setPassword(passwordEncoder.encode(record.password()));
        repository.save(user);
    }

    public ResponseEntity<JJwtResponse> login(Login record) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(
                record.username(), record.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        JJwtResponse jJwtResponse = jwtService.createToken(authentication);
        return ResponseEntity.ok(jJwtResponse);
    }

    public void editUser(Long userId, EditUser record) {
        AppUser appUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        editUserMapper.updateEntity(record, appUser);
        if(record.password() != null)
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        repository.save(appUser);
    }

    public MappingJacksonValue getUsers(AppUserPageRequestDto dto) {
        Page<AppUser> appUserPage = repository.findAll(dto.getPageable());

        Page<AppUserDto> appUserDtoPage = appUserPage.map(appUser -> {
            AppUserDto appUserDto = mapper.toDto(appUser);
            appUserDto.add(linkTo(methodOn(AppUserController.class).getUser(appUser.getId())).withSelfRel());
            return appUserDto;
        });
        PagedModel<AppUserDto> pagedModel = dto.getPagedModel(appUserDtoPage, methodOn(AppUserController.class).getUsers(dto));
        return mjv.listFilter(pagedModel);
    }

    public MappingJacksonValue getUser(Long userId) {
        AppUser appUser = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        AppUserDto dto = mapper.toDto(appUser);
        return mjv.selfFilter(dto);
    }

    public void deleteUsers(List<Long> userIds) {
        repository.deleteAllById(userIds);
    }

    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }

}
