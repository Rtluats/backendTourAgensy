package com.spring.multimodule.service;

import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.mapper.CommentMapper;
import com.spring.multimodule.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
	private final CommentRepository repository;
	private final CommentMapper mapper;

	public CommentService(CommentRepository repository, CommentMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}


	public List<CommentDto> getAll(){

		return repository.findAll().stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public CommentDto getById(Long id){
		return mapper.toDto(repository.findById(id).get());
		//.orElseThrow(
		//() -> new ResourceNotFoundException("Hotel not exist with this id" + id)
		//));
	}

	public CommentDto save(CommentDto ent){

		return mapper.toDto(repository.save(mapper.toEnt(ent)));
	}

	public void delete(CommentDto ent){
		repository.delete(mapper.toEnt(ent));
	}
}
