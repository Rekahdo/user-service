package com.rekahdo.user_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum OTPPurpose {

    ACCOUNT_VERIFICATION("AV", "Account OTP Verification"),
    NUMBER_VERIFICATION("NV", "Phone Number OTP Verification"),
    FORGOT_PASSWORD("FP", "Forgot Password OTP Verification");

    public final String purpose;
    public final String title;

    OTPPurpose(String purpose, String title) {
        this.purpose = purpose;
        this.title = title;
    }

    public static OTPPurpose findByPurpose(String purpose){
        Optional<OTPPurpose> optional = Arrays.stream(OTPPurpose.values())
                .filter(otpPurpose -> Objects.equals(otpPurpose.purpose, purpose))
                .findFirst();
        return optional.orElse(null);
    }


}