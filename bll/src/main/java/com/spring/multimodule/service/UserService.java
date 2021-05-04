package com.spring.multimodule.service;

import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.entity.User;
import com.spring.multimodule.mapper.UserMapper;
import com.spring.multimodule.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository repository;
	private final UserMapper mapper;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);


	public UserService(UserRepository repository, UserMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

//	public static UserDto build(UserDto user) {
//		Set<RoleDto> authorities = user.getRoles();
//
//		return new UserDto(
//				user.getId(),
//				user.getUsername(),
//				user.getEmail(),
//				user.getPassword(),
//				authorities);
//	}


	public List<UserDto> getAll(){
		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public UserDto getById(Long id){
		return mapper.toDto(repository.getOne(id));
	}

	public UserDto save(UserDto ent){

		ent.setPassword(encoder.encode(ent.getPassword()));
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public UserDto update(UserDto ent){
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(UserDto ent){
		repository.delete(mapper.toEnt(ent));
	}

	public Boolean existUserByUsername(String username){
		return repository.existsByUsername(username);
	}

	public Boolean existUserByEmail(String email){
		return repository.existsByEmail(email);
	}

	public UserDto findByUserName(String username){
		return mapper.toDto(repository.findByUsername(username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);

		if(user == null)
			throw new UsernameNotFoundException("User not found");


		return user;
	}
}
