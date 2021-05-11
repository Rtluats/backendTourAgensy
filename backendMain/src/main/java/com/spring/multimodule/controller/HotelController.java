package com.spring.multimodule.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.HotelDto;
import com.spring.multimodule.json.JsonHotelView;
import com.spring.multimodule.service.HotelService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

	private final HotelService hotelService;

	public HotelController(HotelService manager) {
		this.hotelService = manager;
	}

	@GetMapping
	@JsonView(JsonHotelView.IdNameCityPriceList.class)
	public List<HotelDto> getHotels(){ return hotelService.getAll(); }

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JsonHotelView.IdNameCityPriceList.class)
	public ResponseEntity<HotelDto> getHotel(@PathVariable Long id){
		return ResponseEntity.ok(hotelService.getById(id));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonHotelView.IdNameCityPriceList.class)
	public HotelDto addHotel(@RequestBody HotelDto hotel) {
		var city = hotel.getCity();
		hotel.setCity(null);
		hotel = hotelService.save(hotel);
		hotel.setCity(city);
		return hotelService.save(hotel);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonHotelView.IdNameCityPriceList.class)
	public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel){
		var saveHotel = hotelService.getById(id);
		saveHotel.setName(hotel.getName());
		saveHotel.setCity(hotel.getCity());
		return ResponseEntity.ok(hotelService.save(saveHotel));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteHotel(@PathVariable Long id){
		hotelService.delete(hotelService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
