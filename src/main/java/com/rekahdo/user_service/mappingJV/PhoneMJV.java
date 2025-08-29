package com.rekahdo.user_service.mappingJV;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.stereotype.Component;

@Component
public final class PhoneMJV extends MJV{

	public PhoneMJV() {
		SELF = new SimpleFilterProvider()
				.addFilter("phoneDtoFilter", SimpleBeanPropertyFilter.serializeAllExcept());
	}

}
