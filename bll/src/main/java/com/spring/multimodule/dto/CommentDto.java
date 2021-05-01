package com.spring.multimodule.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
@JsonSerialize
public class CommentDto {
	private Long id;
	private String message;
	@CreationTimestamp
	private LocalDateTime localDateTime;
	private UserInfoDto userInfo;
}
