package com.spring.multimodule.json;

public class JsonCommentView {
	public interface Id{}
	public interface IdMessage extends JsonCommentView.Id {}
	public interface IdMessageDate extends JsonCommentView.IdMessage {}
	public interface IdMessageDatePriceList extends JsonCommentView.IdMessageDate, JsonPriceListView.IdPriceDiscountNumberOfDaysHotel {}
	public interface IdMessageDatePriceListUserInfo extends JsonCommentView.IdMessageDatePriceList,  JsonUserInfoView.FLNamePhone {}

}
