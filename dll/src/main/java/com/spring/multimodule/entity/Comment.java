package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String message;

	@Column
	@CreationTimestamp
	private LocalDateTime localDateTime;

	@ManyToOne(cascade = CascadeType.ALL)
	private PriceList priceList;

	@ManyToOne(cascade = CascadeType.ALL)
	private UserInfo userInfo;
}
