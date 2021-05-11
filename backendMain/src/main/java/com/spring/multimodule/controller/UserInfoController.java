package com.spring.multimodule.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.GroupDto;
import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.json.JsonUserInfoView;
import com.spring.multimodule.service.UserInfoService;
import com.spring.multimodule.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/userInfo")
public class UserInfoController {
	private final UserService userService;
	private final UserInfoService userInfoService;

	public UserInfoController(UserInfoService userInfoService, UserService userService) {
		this.userInfoService = userInfoService;
		this.userService = userService;
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasRole('USER')")
	@JsonView(JsonUserInfoView.FLNamePhone.class)
	public UserInfoDto getUserInfoByUserName(@PathVariable String username){
		return userService.findByUserName(username).getUserInfo();
	}

	@GetMapping("/getGroupsByPriceListId/{id}")
	@PreAuthorize("hasRole('USER')")
	@JsonView(JsonUserInfoView.FLNamePhone.class)
	public List<GroupDto> getGroupsByPriceListId(@PathVariable Long id, Authentication authentication){
		var user = userService.findByUserName(authentication.getName());
		var groups = new ArrayList<GroupDto>();
		for (var g: user.getUserInfo().getGroups()){
			if(g.getPriceList().getId().equals(id)){
				groups.add(g);
			}
		}
		return groups;
	}
}
