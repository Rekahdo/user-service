package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.entities.AuthorityDto;
import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Authority;
import com.rekahdo.user_service.exceptions.classes.AuthorityNotFoundException;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.mappers.AssignAuthorityMapper;
import com.rekahdo.user_service.mappers.AuthorityMapper;
import com.rekahdo.user_service.mappingJV.AuthorityMJV;
import com.rekahdo.user_service.repositories.AppUserRepository;
import com.rekahdo.user_service.repositories.AuthorityRepository;
import com.rekahdo.user_service.security.config.Api_UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository repository;
    private final AuthorityMapper mapper;
    private final AuthorityMJV mjv;

    private final AssignAuthorityMapper assignAuthorityMapper;

    private final AppUserRepository appUserRepository;

    @Value("${assignmentKey}")
    private String assignmentKey;

    public void assignAuthority(Long userId, AssignAuthority record, Authentication authentication) {
        if(!appUserRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        if(authentication != null) {
            Api_UserDetails userDetails = (Api_UserDetails) authentication.getPrincipal();
            if(!userDetails.isAdmin() && !Objects.equals(this.assignmentKey, record.assignmentKey()))
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "enter the correct 'assignmentKey'");
        }

        Authority authority = repository.findByAppUserId(userId)
                .orElse(new Authority());

        authority.setAppUser(new AppUser(userId));
        authority.setAssignedAt(LocalDate.now());
        assignAuthorityMapper.updateEntity(record, authority);
        repository.save(authority);
    }

    public MappingJacksonValue getAuthority(Long userId) {
        if(!appUserRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        Authority authority = repository.findByAppUserId(userId)
                .orElseThrow(() -> new AuthorityNotFoundException(userId));

        AuthorityDto dto = mapper.toDto(authority);
        return mjv.selfFilter(dto);
    }

    public void deleteAuthority(Long userId) {
        repository.deleteByAppUserId(userId);
    }

}
