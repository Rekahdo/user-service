package com.rekahdo.user_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum OTPPurpose {

    FORGOT_PASSWORD_BY_EMAIL(1),
    FORGOT_PASSWORD_BY_PHONE(2),
	VERIFY_BY_NUMBER(3),
	VERIFY_BY_EMAIL(4);

    public final Integer index;

    OTPPurpose(Integer index) {
        this.index = index;
    }

    public static OTPPurpose findByIndex(Integer index){
        Optional<OTPPurpose> optional = Arrays.stream(OTPPurpose.values())
                .filter(value -> Objects.equals(value.getIndex(), index))
                .findFirst();
        return optional.orElse(null);
    }

}