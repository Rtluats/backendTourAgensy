package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.HotelDto;
import com.spring.multimodule.entity.Hotel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class HotelMapper {
	private final ModelMapper mapper;

	public HotelMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Hotel toEnt(HotelDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Hotel.class);
	}

	public HotelDto toDto(Hotel ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, HotelDto.class);
	}
}
