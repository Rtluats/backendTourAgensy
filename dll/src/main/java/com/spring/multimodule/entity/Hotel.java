package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	private City city;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<PriceList> priceLists;
}
