package com.spring.multimodule.controller;

import com.spring.multimodule.dto.CityDto;
import com.spring.multimodule.service.CityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/cities")
public class CityController {

	private final CityService manager;

	public CityController(CityService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<CityDto> getCities(){

		return manager.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CityDto> getCity(@PathVariable Long id){
		return ResponseEntity.ok(manager.getById(id));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public CityDto addCity(@RequestBody CityDto cityDto){
		return manager.save(cityDto);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @RequestBody CityDto cityDto){
		var cityDtoSave = manager.getById(id);
		cityDtoSave.setName(cityDto.getName());
		cityDtoSave.setCountryDto(cityDto.getCountryDto());
		return ResponseEntity.ok(manager.save(cityDtoSave));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteCity(@PathVariable Long id){
		manager.delete(manager.getById(id));
		System.out.println(1);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
