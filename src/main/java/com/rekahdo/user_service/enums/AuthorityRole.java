package com.rekahdo.user_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum AuthorityRole {
	
	ADMIN(1),
	MODERATOR(2),
	EDITOR(3);

	public final Integer index;

	AuthorityRole(Integer index) {
		this.index = index;
	}

	public static AuthorityRole findByIndex(Integer index){
		Optional<AuthorityRole> optional = Arrays.stream(AuthorityRole.values())
				.filter(authorityRole -> Objects.equals(authorityRole.getIndex(), index))
				.findFirst();
		return optional.orElse(null);
	}
    
}