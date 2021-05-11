package com.spring.multimodule.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.GroupDto;
import com.spring.multimodule.dto.PriceListDto;
import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.dto.UserInfoDto;
import com.spring.multimodule.json.JsonPriceListView;
import com.spring.multimodule.payload.response.MessageResponse;
import com.spring.multimodule.service.MailService;
import com.spring.multimodule.service.PriceListService;
import com.spring.multimodule.service.UserInfoService;
import com.spring.multimodule.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/priceLists")
public class PriceListController {
	private final MailService mailService;
	private final PriceListService priceListService;
	private final UserService userService;
	private final UserInfoService userInfoService;

	public PriceListController(PriceListService manager, MailService mailService, UserService userService, UserInfoService userInfoService) {
		this.priceListService = manager;
		this.mailService = mailService;
		this.userService = userService;
		this.userInfoService = userInfoService;
	}

	@GetMapping
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public List<PriceListDto> getPriceLists(){
		return priceListService.getAll();
	}

	@GetMapping("/{name}")
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public List<PriceListDto> getPriceListsByCityName(@PathVariable String name){ return priceListService.getPriceListByCityName(name); }

	@GetMapping("/{id}")
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public PriceListDto getPriceListById(@PathVariable Long id){
		return priceListService.getById(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public PriceListDto addPriceList(@RequestBody PriceListDto priceList){
		var hotel = priceList.getHotel();
		priceList.setHotel(null);
		priceList = priceListService.save(priceList);
		priceList.setHotel(hotel);
		return priceListService.save(priceList);
	}

	@PostMapping("/buyATour/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> buyATour(@PathVariable Long id, Authentication authentication){
		try {
			System.out.println(1);
			UserDto user = userService.findByUserName(authentication.getName());
			UserInfoDto info = userInfoService.getById(user.getUserInfo().getId());
			PriceListDto priceList = priceListService.getById(id);
			if (priceList.getGroup() != null){
				priceList.setGroup(new GroupDto());
				priceList.getGroup().setPriceList(priceList);
				priceList.getGroup().setUserInfoList(new HashSet<>());
				priceList.getGroup().getUserInfoList().add(info);
				info.getGroups().add(priceList.getGroup());
				System.out.println(2);
				priceListService.save(priceList); // ?? достаточно?
			}
			System.out.println(3);
			mailService.sendDocToEmail(user.getEmail(), priceListService.getById(id).getTour().getTitle());
		}catch (Exception ex){
			ex.printStackTrace();
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Throw error when try to send doc to email"));
		}
		return ResponseEntity.ok()
				.body(new MessageResponse("Good! Check your email."));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public ResponseEntity<PriceListDto> updatePriceList(@PathVariable Long id, @RequestBody PriceListDto priceList){
		var savePriceList = priceListService.getById(id);
		savePriceList.setPrice(priceList.getPrice());
		savePriceList.setDepartureDate(priceList.getDepartureDate());
		savePriceList.setDiscount(priceList.getDiscount());
		savePriceList.setNumberOfDays(priceList.getNumberOfDays());
		savePriceList.setHotel(priceList.getHotel());
		return ResponseEntity.ok(priceListService.save(savePriceList));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	public ResponseEntity<Map<String, Boolean>> deletePriceList(@PathVariable Long id){
		priceListService.delete(priceListService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
