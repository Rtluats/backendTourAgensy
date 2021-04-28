package com.spring.multimodule.service;

import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.mapper.UserInfoMapper;
import com.spring.multimodule.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoService {
	private final UserInfoRepository repository;
	private final UserInfoMapper mapper;

	public UserInfoService(UserInfoRepository repository, UserInfoMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<UserInfoDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public UserInfoDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
		//.orElseThrow(
		//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public UserInfoDto save(UserInfoDto ent){

		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(UserInfoDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
