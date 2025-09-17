package com.rekahdo.user_service.services;

import com.rekahdo.user_service.controllers.AppUserController;
import com.rekahdo.user_service.controllers.PhoneController;
import com.rekahdo.user_service.dtos.entities.PhoneDto;
import com.rekahdo.user_service.dtos.records.AddPhone;
import com.rekahdo.user_service.dtos.records.EditPhone;
import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Phone;
import com.rekahdo.user_service.exceptions.classes.EmptyListException;
import com.rekahdo.user_service.exceptions.classes.PhoneNotFoundException;
import com.rekahdo.user_service.exceptions.classes.UserPhoneExistsException;
import com.rekahdo.user_service.mappers.AddPhoneMapper;
import com.rekahdo.user_service.mappers.EditPhoneMapper;
import com.rekahdo.user_service.mappers.PhoneMapper;
import com.rekahdo.user_service.mappingJV.PhoneMJV;
import com.rekahdo.user_service.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository repository;
    private final PhoneMapper mapper;
    private final PhoneMJV mjv;

    private final AddPhoneMapper addPhoneMapper;
    private final EditPhoneMapper editPhoneMapper;

    public void addPhone(Long userId, AddPhone record) {
        if(repository.existsByAppUserIdAndCountryCodeAndNumber(userId, record.countryCode(), record.number()))
            throw new UserPhoneExistsException(record.countryCode(), record.number());

        Phone phone = addPhoneMapper.toEntity(record);
        phone.setAppUser(new AppUser(userId));
        repository.save(phone);
    }

    public void editPhone(Long userId, Long phoneId, EditPhone record) {
        if(repository.existsByAppUserIdAndCountryCodeAndNumber(userId, record.countryCode(), record.number()))
            throw new UserPhoneExistsException(record.countryCode(), record.number());

        Optional<Phone> optional = repository.findByIdAndAppUserId(phoneId, userId);
        if(optional.isEmpty()) throw new PhoneNotFoundException();

        Phone phone = optional.get();
        editPhoneMapper.updateEntity(record, phone);
        repository.save(phone);
    }

    public MappingJacksonValue getPhones(Long userId) {
        List<Phone> list = repository.findAllByAppUserId(userId);
        if(list.isEmpty()) throw new EmptyListException();

        List<PhoneDto> dtos = list.stream().map(phone -> {
            PhoneDto dto = mapper.toDto(phone);
            dto.add(linkTo(methodOn(PhoneController.class).getPhone(userId, phone.getId())).withSelfRel());
            return dto;
        }).toList();
        return mjv.listFilter(dtos);
    }

    public MappingJacksonValue getPhone(Long userId, Long phoneId) {
        Optional<Phone> optional = repository.findByIdAndAppUserId(phoneId, userId);
        if(optional.isEmpty()) throw new PhoneNotFoundException();

        PhoneDto record = mapper.toDto(optional.get());
        record.add(linkTo(methodOn(PhoneController.class).getPhones(userId)).withRel("phones"));
        return mjv.selfFilter(record);
    }

    public void deletePhones(Long userId, List<Long> phoneIds) {
        repository.deleteByIdInAndAppUserId(phoneIds, userId);
    }

    public void deletePhone(Long userId, Long phoneId) {
        repository.deleteByIdAndAppUserId(phoneId, userId);
    }

}