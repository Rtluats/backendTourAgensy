package com.spring.multimodule.dto;


import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonHotelView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
	@JsonView(JsonHotelView.Id.class)
	private Long id;
	@JsonView(JsonHotelView.IdName.class)
	private String name;
	@JsonView(JsonHotelView.IdNameCity.class)
	private CityDto city;
	@JsonView(JsonHotelView.IdNameCityPriceList.class)
	private List<PriceListDto> priceLists;
}
