package com.spring.multimodule.service;

import com.spring.multimodule.dto.TourDto;
import com.spring.multimodule.mapper.TourMapper;
import com.spring.multimodule.repository.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TourService {
	private final TourRepository repository;
	private final TourMapper mapper;

	public TourService(TourRepository repository, TourMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<TourDto> getAll(){
		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public List<TourDto> getByTourTitle(String title){
		return repository.findByTitleContainsOrderByTitle(title).stream()
				.filter(tour -> tour.getTitle().contains(title))
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public TourDto getById(Long id){
		return mapper.toDto(repository.getOne(id));
	}

	public TourDto save(TourDto ent){
		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(TourDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
