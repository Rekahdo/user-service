package com.rekahdo.user_service.security.config;

import com.rekahdo.user_service.dtos.records.VerifyAccount;
import com.rekahdo.user_service.dtos.records.VerifyNumber;
import com.rekahdo.user_service.entities.Phone;
import com.rekahdo.user_service.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("userSecurity")
@RequiredArgsConstructor
public class Api_UserSecurity {

	private final PhoneRepository phoneRepository;

	public boolean isUserAuth(Authentication authentication, Long id) {
		Long authId = ((Api_UserDetails) authentication.getPrincipal()).getId();
		return Objects.equals(authId, id);
	}

	public boolean isUserAuth(Authentication authentication, VerifyAccount record) {
		String email = ((Api_UserDetails) authentication.getPrincipal()).getEmail();
		if(!Objects.equals(email, record.email())) throw new AccessDeniedException(String.format(
					"Access denied because email '%s' does not belong to the authenticated user", record.email()));
		return true;
	}

	public boolean isUserAuth(Authentication authentication, VerifyNumber record) {
		Api_UserDetails userDetails = ((Api_UserDetails) authentication.getPrincipal());
		List<Phone> phones = phoneRepository.findAllByAppUserId(userDetails.getId());
		boolean hasNumber = phones.stream().anyMatch(phone -> Objects.equals(phone.phoneNumber(), record.phoneNumber()));
		if(!hasNumber) throw new AccessDeniedException(String.format(
					"Access denied because phone number '%s' does not belong to the authenticated user", record.phoneNumber()));
		return true;
	}

}
