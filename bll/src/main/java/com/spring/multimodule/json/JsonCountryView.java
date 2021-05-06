package com.spring.multimodule.json;

public class JsonCountryView {
	public interface Id{}
	public interface IdName extends JsonCountryView.Id {}
	public interface IdNameCities extends JsonCountryView.IdName {}
}
