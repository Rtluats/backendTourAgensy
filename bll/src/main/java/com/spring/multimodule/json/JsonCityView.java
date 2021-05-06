package com.spring.multimodule.json;

public final class JsonCityView {
	public interface Id{}
	public interface IdName extends Id{}
	public interface IdNameCountry extends IdName{}
	public interface IdNameCountryHotel extends IdNameCountry{}
}
