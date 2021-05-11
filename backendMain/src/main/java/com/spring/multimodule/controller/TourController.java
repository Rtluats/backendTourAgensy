package com.spring.multimodule.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.dto.TourDto;
import com.spring.multimodule.json.JsonTourView;
import com.spring.multimodule.service.PriceListService;
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
	private final PriceListService priceListService;

	public TourController(TourService manager, PriceListService priceListService) {
		this.tourService = manager;
		this.priceListService = priceListService;
	}

	@GetMapping
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	public List<TourDto> getTours(){
		return tourService.getAll();
	}

	@GetMapping("/{id}")
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	public TourDto getTourById(@PathVariable Long id){
		return tourService.getById(id);
	}

	@GetMapping("/byTitle/{title}")
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	public List<TourDto> getTourByTitle(@PathVariable String title){
		return tourService.getByTourTitle(title);
	}

	@PostMapping
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	public TourDto addTour(@RequestBody TourDto tour){
		var priceLists = tour.getPriceLists();
		tour.setPriceLists(null);
		tour = tourService.save(tour);
		for(var p: priceLists){
			p.setTour(tour);
		}
		tour.setPriceLists(priceLists);
		return tourService.save(tour);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('MANAGER')")
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	public ResponseEntity<TourDto> updateTour(@PathVariable Long id, @RequestBody TourDto tour){
		var saveTour = tourService.getById(id);
		saveTour.setDescription(tour.getDescription());
		saveTour.setTitle(tour.getTitle());
		for(var p: tour.getPriceLists()){
			p.setTour(saveTour);
		}
		saveTour.setPriceLists(tour.getPriceLists());
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
