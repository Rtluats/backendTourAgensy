package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.RoleDto;
import com.spring.multimodule.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleMapper {
	private final ModelMapper mapper;

	public RoleMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Role toEnt(RoleDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Role.class);
	}

	public RoleDto toDto(Role ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, RoleDto.class);
	}

}
