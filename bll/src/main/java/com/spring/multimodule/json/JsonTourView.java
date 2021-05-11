package com.spring.multimodule.json;

public class JsonTourView {
	public interface Id{}
	public interface IdTitle extends JsonTourView.Id{}
	public interface IdTitleDescription extends JsonTourView.IdTitle {}
	public interface IdTitleDescriptionPriceLists extends JsonTourView.IdTitleDescription, JsonPriceListView.IdPriceDiscountNumberOfDaysHotel {}
}
