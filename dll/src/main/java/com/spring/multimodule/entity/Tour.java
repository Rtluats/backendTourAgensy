package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String title;
	@Column
	private String description;

	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
	private List<PriceList> priceLists;
}
