package com.spring.multimodule.controller;


import com.spring.multimodule.dto.HotelDto;
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

	private final HotelService manager;

	public HotelController(HotelService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<HotelDto> getHotels(){

		return manager.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelDto> getHotel(@PathVariable Long id){
		return ResponseEntity.ok(manager.getById(id));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public HotelDto addHotel(@RequestBody HotelDto hotel){
		System.out.println(hotel);
		return manager.save(hotel);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel){
		var saveHotel = manager.getById(id);
		saveHotel.setName(hotel.getName());
		return ResponseEntity.ok(manager.save(saveHotel));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteHotel(@PathVariable Long id){
		manager.delete(manager.getById(id));
		System.out.println(1);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
