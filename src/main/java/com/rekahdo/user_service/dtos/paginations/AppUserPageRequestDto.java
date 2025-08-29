package com.rekahdo.user_service.dtos.paginations;

import com.rekahdo.user_service.dtos.entities.AppUserDto;
import com.rekahdo.user_service.entities.AppUser;

public final class AppUserPageRequestDto extends PageRequestDto<AppUser, AppUserDto>{

	public AppUserPageRequestDto() {
		setSize(10);
	}

}