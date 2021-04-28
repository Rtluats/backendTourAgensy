package com.spring.multimodule.service;


import com.spring.multimodule.dto.PriceListDto;
import com.spring.multimodule.mapper.PriceListMapper;
import com.spring.multimodule.repository.PriceListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceListService {
	private final PriceListRepository repository;
	private final PriceListMapper mapper;

	public PriceListService(PriceListRepository repository, PriceListMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<PriceListDto> getAll(){
		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public List<PriceListDto> getPriceListByCityName(String name){
		return repository.getPriceListsByHotelCityName(name).stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public PriceListDto getById(Long id){
		return mapper.toDto(repository.getOne(id));
	}

	public PriceListDto save(PriceListDto ent){
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(PriceListDto ent){
		repository.delete(mapper.toEnt(ent));
	}

}
