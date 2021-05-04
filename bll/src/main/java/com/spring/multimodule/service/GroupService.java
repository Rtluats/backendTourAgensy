package com.spring.multimodule.service;

import com.spring.multimodule.dto.GroupDto;
import com.spring.multimodule.mapper.GroupMapper;
import com.spring.multimodule.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
	private final GroupRepository repository;
	private final GroupMapper mapper;

	public GroupService(GroupRepository repository, GroupMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<GroupDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public GroupDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
		//.orElseThrow(
		//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public GroupDto save(GroupDto ent){

		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(GroupDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
