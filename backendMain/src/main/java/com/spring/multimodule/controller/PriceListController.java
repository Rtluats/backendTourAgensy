package com.spring.multimodule.controller;


import com.spring.multimodule.dto.PriceListDto;
import com.spring.multimodule.service.PriceListService;
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
	private final PriceListService manager;


	public PriceListController(PriceListService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<PriceListDto> getPriceLists(){
		return manager.getAll();
	}

	@GetMapping("/{name}")
	public List<PriceListDto> getPriceListsByCityName(@PathVariable String name){
		return manager.getPriceListByCityName(name);
	}

	@GetMapping("/{id}")
	public PriceListDto getPriceListById(@PathVariable Long id){
		return manager.getById(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public PriceListDto addPriceList(@RequestBody PriceListDto priceList){
		return manager.save(priceList);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<PriceListDto> updatePriceList(@PathVariable Long id, @RequestBody PriceListDto priceList){
		System.out.println(1);
		var savePriceList = manager.getById(id);
		savePriceList.setPrice(priceList.getPrice());
		savePriceList.setHotelDto(priceList.getHotelDto());
		savePriceList.setDepartureDate(priceList.getDepartureDate());
		savePriceList.setDiscount(priceList.getDiscount());
		savePriceList.setNumberOfDays(priceList.getNumberOfDays());
		savePriceList.setTourDto(priceList.getTourDto());
		return ResponseEntity.ok(manager.save(savePriceList));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deletePriceList(@PathVariable Long id){
		manager.delete(manager.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
