package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.entity.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserInfoMapper {
	private final ModelMapper mapper;

	public UserInfoMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public UserInfo toEnt(UserInfoDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, UserInfo.class);
	}

	public UserInfoDto toDto(UserInfo ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, UserInfoDto.class);
	}
}
