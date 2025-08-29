package com.rekahdo.user_service.mappingJV;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

@Component
public final class FindAccountMJV extends MJV{

	public FindAccountMJV() {
		LIST_FILTER = new SimpleFilterProvider().addFilter("appUserDtoFilter",
				SimpleBeanPropertyFilter.filterOutAllExcept("username", "id"));
	}

}
