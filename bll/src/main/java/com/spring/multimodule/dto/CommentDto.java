package com.spring.multimodule.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.multimodule.json.JsonCommentView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	@JsonView(JsonCommentView.Id.class)
	private Long id;
	@JsonView(JsonCommentView.IdMessage.class)
	private String message;
	@JsonView(JsonCommentView.IdMessageDate.class)
	private LocalDateTime localDateTime;
	@JsonView(JsonCommentView.IdMessageDatePriceList.class)
	private PriceListDto priceList;
	@JsonView(JsonCommentView.IdMessageDatePriceListUserInfo.class)
	private UserInfoDto userInfo;
}
