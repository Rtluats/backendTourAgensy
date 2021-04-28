package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.ERoleDto;
import com.spring.multimodule.entity.ERole;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ERoleMapper {
	private final ModelMapper mapper;

	public ERoleMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public ERole toEnt(ERoleDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, ERole.class);
	}

	public ERoleDto toDto(ERole ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, ERoleDto.class);
	}
}
