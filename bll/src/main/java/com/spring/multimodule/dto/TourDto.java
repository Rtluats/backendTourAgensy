package com.spring.multimodule.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonTourView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
	@JsonView(JsonTourView.Id.class)
	private Long id;
	@JsonView(JsonTourView.IdTitle.class)
	private String title;
	@JsonView(JsonTourView.IdTitleDescription.class)
	private String description;
	@JsonView(JsonTourView.IdTitleDescriptionPriceLists.class)
	private List<PriceListDto> priceLists;
}
