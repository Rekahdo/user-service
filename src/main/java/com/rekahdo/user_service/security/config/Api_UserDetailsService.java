package com.rekahdo.user_service.security.config;

import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.enums.OTPPurpose;
import com.rekahdo.user_service.exceptions.classes.AccountNotVerifiedException;
import com.rekahdo.user_service.exceptions.classes.UserNotFoundException;
import com.rekahdo.user_service.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Api_UserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = repository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UserNotFoundException(username));

		if(!appUser.isVerified()) throw new AccountNotVerifiedException();
		return new Api_UserDetails(appUser);
	}
}
