package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
	private final ModelMapper mapper;

	public UserMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public User toEnt(UserDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, User.class);
	}

	public UserDto toDto(User ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, UserDto.class);
	}

	public UserDto toDto(Object ent){
		return Objects.isNull((User)ent) ? null : mapper.map((User)ent, UserDto.class);
	}

}
