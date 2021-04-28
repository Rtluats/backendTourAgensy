package com.spring.multimodule.controller;

import com.spring.multimodule.dto.TourDto;
import com.spring.multimodule.service.TourService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tours")
public class TourController {
	private final TourService manager;


	public TourController(TourService manager) {
		this.manager = manager;
	}

	@GetMapping
	public List<TourDto> getTours(){
		return manager.getAll();
	}

	@GetMapping("/{id}")
	public TourDto getTourById(@PathVariable Long id){
		return manager.getById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('MANAGER')")
	public TourDto addTour(@RequestBody TourDto tour){
		return manager.save(tour);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<TourDto> updateTour(@PathVariable Long id, @RequestBody TourDto tour){
		var saveTour = manager.getById(id);
		saveTour.setDescription(tour.getDescription());
		//saveTour.setPriceLists(tour.getPriceLists());
		saveTour.setTitle(tour.getTitle());
		return ResponseEntity.ok(manager.save(saveTour));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteTour(@PathVariable Long id){
		manager.delete(manager.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
