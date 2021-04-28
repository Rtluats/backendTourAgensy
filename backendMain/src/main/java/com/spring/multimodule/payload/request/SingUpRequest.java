package com.spring.multimodule.payload.request;

import com.spring.multimodule.dto.RoleDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {
	@NotBlank
	@Size(min=8, max=20)
	private String username;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Size(min=8, max=20)
	private String password;

	private Set<RoleDto> roles;
}
