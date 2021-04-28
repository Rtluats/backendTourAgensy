package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.PriceListDto;
import com.spring.multimodule.entity.PriceList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PriceListMapper {
	private final ModelMapper mapper;

	public PriceListMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public PriceList toEnt(PriceListDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, PriceList.class);
	}

	public PriceListDto toDto(PriceList ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, PriceListDto.class);
	}
}
