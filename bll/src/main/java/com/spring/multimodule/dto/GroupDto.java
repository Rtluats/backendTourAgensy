package com.spring.multimodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
	private Long id;
	private HashSet<UserInfoDto> userInfoList;
	private PriceListDto priceList;
}
