package com.spring.multimodule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonPriceListView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListDto {
	@JsonView(JsonPriceListView.Id.class)
	private Long id;
	@JsonView(JsonPriceListView.IdPrice.class)
	private Double price;
	@JsonView(JsonPriceListView.IdPriceDiscount.class)
	private Double discount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(JsonPriceListView.IdPriceDiscountDate.class)
	private Date departureDate;
	@JsonView(JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class)
	private Integer numberOfDays;
	//private byte[] image;
	@JsonView({JsonPriceListView.IdPriceDiscountNumberOfDaysHotel.class})
	private HotelDto hotel;
	@JsonView(JsonPriceListView.IPDNODHT.class)
	private TourDto tour;
	@JsonView(JsonPriceListView.IPDNODHTG.class )
	private GroupDto group;
	private List<CommentDto> comments;
}
