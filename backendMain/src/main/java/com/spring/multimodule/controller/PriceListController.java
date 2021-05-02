package com.spring.multimodule.controller;


import com.spring.multimodule.dto.PriceListDto;
import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.payload.response.MessageResponse;
import com.spring.multimodule.service.MailService;
import com.spring.multimodule.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/priceLists")
public class PriceListController {
	private final MailService mailService;
	private final PriceListService priceListService;

	public PriceListController(PriceListService manager, MailService mailService) {
		this.priceListService = manager;
		this.mailService = mailService;
	}

	@GetMapping
	public List<PriceListDto> getPriceLists(){
		return priceListService.getAll();
	}

	@GetMapping("/{name}")
	public List<PriceListDto> getPriceListsByCityName(@PathVariable String name){ return priceListService.getPriceListByCityName(name); }

	@GetMapping("/{id}")
	public PriceListDto getPriceListById(@PathVariable Long id){
		return priceListService.getById(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public PriceListDto addPriceList(@RequestBody PriceListDto priceList){
		return priceListService.save(priceList);
	}

	@PostMapping("/byuATour/{id}")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<?> buyATour(@PathVariable Long id, @Autowired UserDto user){
		try {
			mailService.sendDocToEmail(user.getEmail(), priceListService.getById(id).getTour().getTitle());
		}catch (Exception ex){
			ResponseEntity.badRequest()
					.body(new MessageResponse("Throw error when try to send doc to email"));
		}
		return ResponseEntity.ok()
				.body(new MessageResponse("Good! Check your email."));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
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
	public ResponseEntity<Map<String, Boolean>> deletePriceList(@PathVariable Long id){
		priceListService.delete(priceListService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
