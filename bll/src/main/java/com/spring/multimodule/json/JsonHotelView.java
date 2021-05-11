package com.spring.multimodule.json;

public final class JsonHotelView {
	public interface Id{}
	public interface IdName extends JsonHotelView.Id {}
	public interface IdNameCity extends JsonHotelView.IdName, JsonCityView.IdNameCountry{}
	public interface IdNameCityPriceList extends JsonHotelView.IdNameCity {}
}
