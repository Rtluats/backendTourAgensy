package com.spring.multimodule.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String phone;

	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;

	@OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToMany(mappedBy = "userInfoList")
	private Set<Group> groups;
}
