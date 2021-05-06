package com.spring.multimodule.json;

public final class JsonHotelView {
	public interface Id{}
	public interface IdName extends JsonHotelView.Id {}
	public interface IdNameCity extends JsonHotelView.IdName {}
	public interface IdNameCityPriceList extends JsonHotelView.IdNameCity, JsonCityView.IdNameCountryHotel{}
}
