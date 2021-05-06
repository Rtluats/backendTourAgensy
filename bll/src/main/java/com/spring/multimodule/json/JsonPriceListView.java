package com.spring.multimodule.json;

public class JsonPriceListView {
	public interface Id{}
	public interface IdPrice extends JsonPriceListView.Id {}
	public interface IdPriceDiscount extends JsonPriceListView.IdPrice {}
	public interface IdPriceDiscountDate extends JsonPriceListView.IdPriceDiscount{}
	public interface IdPriceDiscountNumberOfDays extends JsonPriceListView.IdPriceDiscountDate {}
	public interface IdPriceDiscountNumberOfDaysHotel extends JsonPriceListView.IdPriceDiscountNumberOfDays, JsonHotelView.IdNameCity{}
	public interface IPDNODHT extends JsonPriceListView.IdPriceDiscountNumberOfDaysHotel{}
	public interface IPDNODHTG extends JsonPriceListView.IPDNODHT{}
}
