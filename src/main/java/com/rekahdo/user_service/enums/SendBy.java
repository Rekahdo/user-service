package com.rekahdo.user_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum SendBy {

	EMAIL(0),
	PHONE(1);

	public final Integer index;

	SendBy(Integer index) {
		this.index = index;
	}

	public static SendBy findByIndex(Integer index){
		Optional<SendBy> optional = Arrays.stream(SendBy.values())
				.filter(value -> Objects.equals(value.getIndex(), index))
				.findFirst();
		return optional.orElse(null);
	}
    
}