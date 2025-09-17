package com.rekahdo.user_service.security.config;

import com.rekahdo.user_service.entities.AppUser;
import com.rekahdo.user_service.entities.Phone;
import com.rekahdo.user_service.enums.AuthorityRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Api_UserDetails implements UserDetails {

	private final AppUser appUser;

	public Api_UserDetails(AppUser appUser) {
		this.appUser = appUser;
	}

	// Custom method to get user ID
	public Long getId() {
		return appUser.getId();
	}

    @Override
	public String getUsername() {
		return appUser.getUsername();
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	public String getEmail() {
		return appUser.getEmail();
	}

	public List<Phone> getPhones() {
		return appUser.getPhones();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getAuthority() == null ? List.of(new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority(String.format("ROLE_%s",
				AuthorityRole.findByIndex(appUser.getAuthority().getRole()))));
	}

	@Override
	public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

	@Override
	public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

	@Override
	public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

	@Override
	public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

	// Helper method to check if user has admin role
	public boolean isAdmin() {
		return getAuthorities().stream()
				.anyMatch(authority ->
						authority.getAuthority().equals("ROLE_" + AuthorityRole.ADMIN));
	}

	public boolean isEditor() {
		return getAuthorities().stream()
				.anyMatch(authority ->
						authority.getAuthority().equals("ROLE_" + AuthorityRole.EDITOR));
	}

	public boolean isModerator() {
		return getAuthorities().stream()
				.anyMatch(authority ->
						authority.getAuthority().equals("ROLE_" + AuthorityRole.MODERATOR));
	}

}