package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<UserInfo> userInfoList;

	@ManyToOne(cascade = CascadeType.ALL)
	private PriceList priceList;
}
