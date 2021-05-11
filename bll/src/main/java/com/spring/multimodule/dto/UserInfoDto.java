package com.spring.multimodule.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonUserInfoView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
	private Long id;
	@JsonView(JsonUserInfoView.FirstName.class)
	private String firstName;
	@JsonView(JsonUserInfoView.FLName.class)
	private String lastName;
	@JsonView(JsonUserInfoView.FLNamePhone.class)
	private String phone;
	private UserDto user;
	private List<CommentDto> comments;
	private Set<GroupDto> groups;
}
