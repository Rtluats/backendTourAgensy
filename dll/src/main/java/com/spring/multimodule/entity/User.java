package com.spring.multimodule.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "password")
@Entity
@Table(name = "usr",
	uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
	}
)
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	@Size(min=8, max=20)
	public String username;

	@Column
	@Email
	public String email;

	@Column
	@Size(min=8, max=20)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usr_roles",
			joinColumns = {@JoinColumn(name = "usr_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")}
	)
	private Set<Role> roles = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	private UserInfo userInfo;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
