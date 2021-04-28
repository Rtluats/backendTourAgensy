package com.spring.multimodule.controller;

import com.spring.multimodule.dto.CountryDto;
import com.spring.multimodule.service.CountryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/counties")
public class CountryController {

	private final CountryService manager;

	public CountryController(CountryService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<CountryDto> getCountries(){

		return manager.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CountryDto> getCountry(@PathVariable Long id){
		return ResponseEntity.ok(manager.getById(id));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public CountryDto addCountry(@RequestBody CountryDto countryDto){
		return manager.save(countryDto);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto){
		var countryDto1 = manager.getById(id);
		countryDto1.setName(countryDto.getName());
		return ResponseEntity.ok(manager.save(countryDto1));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteCountry(@PathVariable Long id){
		manager.delete(manager.getById(id));
		System.out.println(1);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
