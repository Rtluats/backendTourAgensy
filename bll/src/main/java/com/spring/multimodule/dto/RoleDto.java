package com.spring.multimodule.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class RoleDto implements GrantedAuthority {
	private Long id;
	private ERoleDto name;
	private Set<UserDto> users;

	public RoleDto(ERoleDto name){
		this.name =  name;
	}

	@Override
	public String getAuthority() {
		return name.name();
	}
}
