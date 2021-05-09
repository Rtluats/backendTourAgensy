package com.spring.multimodule.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.CountryDto;
import com.spring.multimodule.json.JsonCountryView;
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
@RequestMapping("api/v1/countries")
public class CountryController {

	private final CountryService countryService;

	public CountryController(CountryService manager) {
		this.countryService = manager;
	}

	@GetMapping
	@JsonView(JsonCountryView.IdNameCities.class)
	public List<CountryDto> getCountries(){
		return countryService.getAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JsonCountryView.IdNameCities.class)
	public ResponseEntity<CountryDto> getCountry(@PathVariable Long id){
		return ResponseEntity.ok(countryService.getById(id));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonCountryView.IdNameCities.class)
	public CountryDto addCountry(@RequestBody CountryDto countryDto){
		var cities = countryDto.getCities();
		countryDto.setCities(null);
		countryDto = countryService.save(countryDto);
		countryDto.setCities(cities);
		return countryService.save(countryDto);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonCountryView.IdNameCities.class)
	public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto){
		var countryDto1 = countryService.getById(id);
		countryDto1.setName(countryDto.getName());
		countryDto1.setCities(countryDto.getCities());
		System.out.println(countryDto1);
		return ResponseEntity.ok(countryService.save(countryDto1));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteCountry(@PathVariable Long id){
		countryService.delete(countryService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
