package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.GroupDto;
import com.spring.multimodule.entity.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GroupMapper {
	private final ModelMapper mapper;

	public GroupMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Group toEnt(GroupDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Group.class);
	}

	public GroupDto toDto(Group ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, GroupDto.class);
	}

}
