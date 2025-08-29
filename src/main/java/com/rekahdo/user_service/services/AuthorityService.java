package com.rekahdo.user_service.services;

import com.rekahdo.user_service.dtos.entities.AuthorityDto;
import com.rekahdo.user_service.dtos.records.AssignAuthority;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Authority;
import com.rekahdo.user_service.exceptions.classes.AuthorityNotFoundException;
import com.rekahdo.user_service.mappers.AssignAuthorityMapper;
import com.rekahdo.user_service.mappers.AuthorityMapper;
import com.rekahdo.user_service.mappingJV.AuthorityMJV;
import com.rekahdo.user_service.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository repository;
    private final AuthorityMapper mapper;
    private final AuthorityMJV mjv;

    private final AssignAuthorityMapper assignAuthorityMapper;

    public void assignAuthority(Long userId, AssignAuthority record) {
        Authority authority = repository.findByAppUserId(userId)
                .orElse(new Authority());

        authority.setAppUser(new AppUser(userId));
        assignAuthorityMapper.updateEntity(record, authority);
        repository.save(authority);
    }

    public MappingJacksonValue getAuthority(Long userId) {
        Authority authority = repository.findByAppUserId(userId)
                .orElseThrow(() -> new AuthorityNotFoundException(userId));

        AuthorityDto dto = mapper.toDto(authority);
        return mjv.selfFilter(dto);
    }

    public void deleteAuthority(Long userId) {
        repository.deleteByAppUserId(userId);
    }
}
