package com.spring.multimodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
	private Long id;
	private List<UserInfoDto> userInfoList;
	private PriceListDto priceList;
}
