package com.spring.multimodule.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonCityView;
import com.spring.multimodule.json.JsonCountryView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
	@JsonView({JsonCountryView.Id.class, JsonCityView.IdNameCountryHotel.class})
	private Long id;
	@JsonView({JsonCountryView.IdName.class , JsonCityView.IdNameCountryHotel.class})
	private String name;
	@JsonView(JsonCountryView.IdNameCities.class)
	private List<CityDto> cities;
}
