package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.TourDto;
import com.spring.multimodule.entity.Tour;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TourMapper {
	private final ModelMapper mapper;

	public TourMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Tour toEnt(TourDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Tour.class);
	}

	public TourDto toDto(Tour ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, TourDto.class);
	}
}
