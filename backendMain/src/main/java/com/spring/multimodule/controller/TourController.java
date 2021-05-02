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
	private final TourService tourService;


	public TourController(TourService manager) {
		this.tourService = manager;
	}

	@GetMapping
	public List<TourDto> getTours(){
		return tourService.getAll();
	}

	@GetMapping("/{id}")
	public TourDto getTourById(@PathVariable Long id){
		return tourService.getById(id);
	}

	@GetMapping("/byTitle/{title}")
	public List<TourDto> getTourByTitle(@PathVariable String title){
		return tourService.getByTourTitle(title);
	}

	@PostMapping
	@PreAuthorize("hasRole('MANAGER')")
	public TourDto addTour(@RequestBody TourDto tour){
		return tourService.save(tour);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<TourDto> updateTour(@PathVariable Long id, @RequestBody TourDto tour){
		var saveTour = tourService.getById(id);
		saveTour.setDescription(tour.getDescription());
		//saveTour.setPriceLists(tour.getPriceLists());
		saveTour.setTitle(tour.getTitle());
		return ResponseEntity.ok(tourService.save(saveTour));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, Boolean>> deleteTour(@PathVariable Long id){
		tourService.delete(tourService.getById(id));
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
