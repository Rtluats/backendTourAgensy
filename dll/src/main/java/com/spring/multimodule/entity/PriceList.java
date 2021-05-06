package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Double price;
	@Column
	private Double discount;
	@Column
	private Date departureDate;
	@Column
	private Integer numberOfDays;

//	@Lob
//	@Column
//	private byte[] image;


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tour_id")
	private Tour tour;

	@OneToOne(cascade = CascadeType.ALL)
	private Group group;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments;

}
