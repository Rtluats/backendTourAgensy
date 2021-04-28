package com.spring.multimodule.service;

import com.spring.multimodule.dto.ERoleDto;
import com.spring.multimodule.dto.RoleDto;
import com.spring.multimodule.mapper.ERoleMapper;
import com.spring.multimodule.mapper.RoleMapper;
import com.spring.multimodule.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
	private final RoleRepository repository;
	private final RoleMapper mapper;
	private final ERoleMapper eRoleMapper;

	public RoleService(RoleRepository repository, RoleMapper mapper, ERoleMapper eRoleMapper) {
		this.repository = repository;
		this.mapper = mapper;
		this.eRoleMapper = eRoleMapper;
	}

	public List<RoleDto> getAll(){
		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public RoleDto findByName(ERoleDto name){
		return mapper.toDto(repository.findByName(eRoleMapper.toEnt(name))
				.orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
	}

	public RoleDto getById(Long id){
		return mapper.toDto(repository.getOne(id));
	}

	public RoleDto save(RoleDto ent){
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(RoleDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
