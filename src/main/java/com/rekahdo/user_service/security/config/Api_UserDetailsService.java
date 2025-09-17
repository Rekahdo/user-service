package com.rekahdo.user_service.security.config;

import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Api_UserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = repository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
		return new Api_UserDetails(appUser);
	}
}
