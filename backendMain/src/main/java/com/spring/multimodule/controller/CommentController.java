package com.spring.multimodule.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.CommentDto;
import com.spring.multimodule.json.JsonCommentView;
import com.spring.multimodule.service.CommentService;
import com.spring.multimodule.service.PriceListService;
import com.spring.multimodule.service.UserInfoService;
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
	private final UserInfoService userInfoService;
	private final PriceListService priceListService;
	private final CommentService commentService;
	private final UserService userService;

	public CommentController(PriceListService priceListRepository, CommentService manager, UserService userService, UserInfoService userInfoService) {
		this.priceListService = priceListRepository;
		this.commentService = manager;
		this.userService = userService;
		this.userInfoService = userInfoService;
	}

	@GetMapping
	@JsonView(JsonCommentView.IdMessageDatePriceListUserInfo.class)
	public List<CommentDto> getComments(){
		return commentService.getAll();
	}

	@GetMapping("byPriceListId/{id}")
	@JsonView(JsonCommentView.IdMessageDatePriceListUserInfo.class)
	public List<CommentDto> getCommentByPriceListDto(@PathVariable Long id){
		return commentService.findAllByPriceListIdOrderByDateTime(id);
	}


	@PostMapping(value = "commentToPriceList/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	@JsonView(JsonCommentView.IdMessageDatePriceListUserInfo.class)
	public CommentDto addComment(@PathVariable Long id, @RequestBody CommentDto commentDto, Authentication authentication){
		var user = userService.findByUserName(authentication.getName());
		var priceList = priceListService.getById(id);
		commentDto.setPriceList(null);
		commentDto.setLocalDateTime(LocalDateTime.now());
		commentDto = commentService.save(commentDto);
		priceList.getComments().add(commentDto);
		priceListService.save(priceList);
		user.getUserInfo().getComments().add(commentDto);
		userInfoService.save(user.getUserInfo());
		return commentService.getById(commentDto.getId());
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
