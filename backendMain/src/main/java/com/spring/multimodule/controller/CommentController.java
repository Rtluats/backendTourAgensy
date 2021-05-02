package com.spring.multimodule.controller;

import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.service.CommentService;
import com.spring.multimodule.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
	private final PriceListService priceListService;
	private final CommentService commentService;

	public CommentController(PriceListService priceListRepository, CommentService manager) {
		this.priceListService = priceListRepository;
		this.commentService = manager;
	}

	@GetMapping
	public List<CommentDto> getComments(){

		return commentService.getAll();
	}

	@GetMapping("byPriceListId/{id}")
	public List<CommentDto> getCommentByPriceListDto(@PathVariable Long id){
		return commentService.findAllByPriceListIdOrderByDateTime(id);
	}


	@PostMapping(value = "commentToPriceList/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDto commentDto, @Autowired UserDto user){
		commentDto.setUserInfo(user.getUserInfo());
		commentDto.setPriceList(priceListService.getById(id));
		commentDto.setLocalDateTime(LocalDateTime.now());
		return commentService.save(commentDto);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteComment(@PathVariable Long id){
		commentService.delete(commentService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
