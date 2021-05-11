package com.spring.multimodule.json;

public class JsonUserInfoView {
	public interface FirstName {}
	public interface FLName extends FirstName {}
	public interface FLNamePhone extends FLName {}
}
