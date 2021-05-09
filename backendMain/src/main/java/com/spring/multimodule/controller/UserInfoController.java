package com.spring.multimodule.controller;

import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.service.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/userInfo")
public class UserInfoController {
	private final UserInfoService userInfoService;

	public UserInfoController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasRole('USER')")
	public UserInfoDto getUserInfoByUserName(@PathVariable String username){
		return userInfoService.getUserInfoByUserName(username);
	}
}
