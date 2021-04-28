package com.spring.multimodule.payload.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.multimodule.dto.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@JsonSerialize
@JsonDeserialize
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, Set<RoleDto> roles){
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles.stream().map(RoleDto::getName).map(Enum::name).toList();
	}

}
