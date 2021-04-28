package com.spring.multimodule.service;

import com.spring.multimodule.dto.CityDto;
import com.spring.multimodule.mapper.CityMapper;
import com.spring.multimodule.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
	private final CityRepository repository;
	private final CityMapper mapper;

	public CityService(CityRepository repository, CityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<CityDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public CityDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
		//.orElseThrow(
		//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public CityDto save(CityDto ent){

		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(CityDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
