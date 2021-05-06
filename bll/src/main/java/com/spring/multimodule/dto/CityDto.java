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
public class CityDto {
	@JsonView({JsonCityView.Id.class, JsonCountryView.IdNameCities.class,})
	private Long id;
	@JsonView({JsonCityView.IdName.class, JsonCountryView.IdNameCities.class})
	private String name;
	@JsonView({JsonCityView.IdNameCountry.class})
	private CountryDto country;
	@JsonView({JsonCityView.IdNameCountryHotel.class, JsonCountryView.IdNameCities.class})
	private List<HotelDto> hotels;
}
