package com.spring.multimodule.controller;

import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.service.CommentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

	private final CommentService manager;

	public CommentController(CommentService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<CommentDto> getComments(){

		return manager.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDto> getComment(@PathVariable Long id){
		return ResponseEntity.ok(manager.getById(id));
	}

	@GetMapping("by-price-list-id/{id}")
	public List<CommentDto> getCommentByPriceListDto(@PathVariable Long id){
		return manager.findAllByPriceListIdOrderByDateTime(id);
	}


	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CommentDto addComment(@RequestBody CommentDto commentDto){
		return manager.save(commentDto);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto){
		var commentDto1 = manager.getById(id);
		commentDto1.setMessage(commentDto.getMessage());
		return ResponseEntity.ok(manager.save(commentDto1));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteComment(@PathVariable Long id){
		manager.delete(manager.getById(id));
		System.out.println(1);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
