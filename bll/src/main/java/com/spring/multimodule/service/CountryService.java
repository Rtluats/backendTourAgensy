package com.spring.multimodule.service;

import com.spring.multimodule.dto.CountryDto;
import com.spring.multimodule.mapper.CountryMapper;
import com.spring.multimodule.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
	private final CountryRepository repository;
	private final CountryMapper mapper;

	public CountryService(CountryRepository repository, CountryMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<CountryDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public CountryDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
		//.orElseThrow(
		//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public CountryDto save(CountryDto ent){
		if (ent.getCities() != null){
			for(var el : ent.getCities()){
				el.setCountry(ent);
			}
		}
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(CountryDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
