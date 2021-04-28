package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.CountryDto;
import com.spring.multimodule.entity.Country;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CountryMapper {
	private final ModelMapper mapper;

	public CountryMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Country toEnt(CountryDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Country.class);
	}

	public CountryDto toDto(Country ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, CountryDto.class);
	}
}
