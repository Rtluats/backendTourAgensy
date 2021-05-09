package com.spring.multimodule.controller;

import com.spring.multimodule.dto.ERoleDto;
import com.spring.multimodule.dto.RoleDto;
import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.mapper.UserMapper;
import com.spring.multimodule.payload.request.LoginRequest;
import com.spring.multimodule.payload.request.SingUpRequest;
import com.spring.multimodule.payload.response.JwtResponse;
import com.spring.multimodule.payload.response.MessageResponse;
import com.spring.multimodule.security.jwt.JwtUtils;
import com.spring.multimodule.service.RoleService;
import com.spring.multimodule.service.UserInfoService;
import com.spring.multimodule.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	private final UserInfoService userInfoService;
	private final UserMapper mapper;
	private final AuthenticationManager authManager;
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;

	public AuthController(AuthenticationManager authManager, UserService userService, RoleService roleService, PasswordEncoder encoder, JwtUtils jwtUtils, UserMapper mapper, UserInfoService userInfoService) {
		this.authManager = authManager;
		this.userService = userService;
		this.roleService = roleService;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
		this.mapper = mapper;
		this.userInfoService = userInfoService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest){
		System.out.println(encoder.encode(loginRequest.getPassword()));
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		UserDto user = mapper.toDto(auth.getPrincipal());
		return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), user.getRoles()));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser( @RequestBody SingUpRequest singUpRequest){
		if (userService.existUserByUsername(singUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existUserByEmail(singUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		UserDto user = new UserDto(singUpRequest.getUsername(),
				encoder.encode(singUpRequest.getPassword()),
				singUpRequest.getEmail());

		Set<RoleDto> rolesU = singUpRequest.getRoles();

		if (rolesU == null) {
			rolesU = new HashSet<>();
			RoleDto userRole = roleService.findByName(ERoleDto.ROLE_USER);
			rolesU.add(userRole);
		}

		user.setRoles(rolesU);
		var userInfo = new UserInfoDto();
		userInfo.setFirstName(singUpRequest.getFirstName());
		userInfo.setLastName(singUpRequest.getLastName());
		userInfo.setPhone(singUpRequest.getPhone());
		userInfo = userInfoService.save(userInfo);
		user = userService.save(user);
		user.setUserInfo(userInfo);
		userService.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}


}
