package com.spring.multimodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "grp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@CreationTimestamp
	private LocalDateTime localDateTime;
	
	@ManyToMany
	@JoinTable(name = "grp_user_info",
			joinColumns = {@JoinColumn(name = "grp_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name="user_info_id", referencedColumnName = "id")}
	)
	private Set<UserInfo> userInfoList;

	@ManyToOne(cascade = CascadeType.ALL)
	private PriceList priceList;
}
