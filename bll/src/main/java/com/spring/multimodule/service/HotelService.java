package com.spring.multimodule.service;

import com.spring.multimodule.dto.HotelDto;
import com.spring.multimodule.mapper.HotelMapper;
import com.spring.multimodule.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
	private final HotelRepository repository;
	private final HotelMapper mapper;

	public HotelService(HotelRepository repository, HotelMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<HotelDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public HotelDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
				//.orElseThrow(
				//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public HotelDto save(HotelDto ent){

		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(HotelDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
