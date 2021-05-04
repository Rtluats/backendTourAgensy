package com.spring.multimodule.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class UserInfoDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private UserDto user;
	private List<CommentDto> comments;
	private GroupDto group;
}
