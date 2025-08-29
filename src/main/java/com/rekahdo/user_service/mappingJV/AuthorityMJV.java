package com.rekahdo.user_service.mappingJV;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.stereotype.Component;

@Component
public final class AuthorityMJV extends MJV{

	public AuthorityMJV() {
		SELF = new SimpleFilterProvider()
				.addFilter("authorityDtoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("_links", "role", "assignedAt"));
	}

}
