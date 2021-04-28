package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.CityDto;
import com.spring.multimodule.entity.City;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CityMapper {
	private final ModelMapper mapper;

	public CityMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public City toEnt(CityDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, City.class);
	}

	public CityDto toDto(City ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, CityDto.class);
	}
}
