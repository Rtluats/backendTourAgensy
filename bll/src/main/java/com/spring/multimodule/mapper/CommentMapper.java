package com.spring.multimodule.mapper;

import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentMapper {
	private final ModelMapper mapper;

	public CommentMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Comment toEnt(CommentDto dto){
		return Objects.isNull(dto) ? null : mapper.map(dto, Comment.class);
	}

	public CommentDto toDto(Comment ent){
		return Objects.isNull(ent) ? null : mapper.map(ent, CommentDto.class);
	}
}
