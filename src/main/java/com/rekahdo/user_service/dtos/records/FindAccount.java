package com.rekahdo.user_service.dtos.records;

import com.rekahdo.user_service.dtos.Dto;
import com.rekahdo.user_service.enums.AuthorityRole;
import com.rekahdo.user_service.enums.FindBy;
import jakarta.validation.constraints.NotNull;

public record FindAccount(
        @NotNull(message = "findBy can not be null") FindBy findBy,
        @NotNull(message = "value can not be null") String value
) implements Dto {

}
