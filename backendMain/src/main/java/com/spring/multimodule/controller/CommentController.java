package com.spring.multimodule.controller;

import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.service.CommentService;
import com.spring.multimodule.service.PriceListService;
import com.spring.multimodule.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
	private final UserService userService;

	public CommentController(PriceListService priceListRepository, CommentService manager, UserService userService) {
		this.priceListService = priceListRepository;
		this.commentService = manager;
		this.userService = userService;
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
	public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDto commentDto, Authentication authentication){
		var user = userService.findByUserName(authentication.getName());
		commentDto.setUserInfo(user.getUserInfo());
		user.getUserInfo().getComments().add(commentDto);
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
