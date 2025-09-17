package com.rekahdo.user_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum FindBy {

	EMAIL(0),
	NUMBER(1);

	public final Integer index;

	FindBy(Integer index) {
		this.index = index;
	}

	public static FindBy findByIndex(Integer index){
		Optional<FindBy> optional = Arrays.stream(FindBy.values())
				.filter(value -> Objects.equals(value.getIndex(), index))
				.findFirst();
		return optional.orElse(null);
	}
    
}