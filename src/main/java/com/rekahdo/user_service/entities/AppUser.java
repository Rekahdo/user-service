package com.rekahdo.user_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String email;

	private boolean verified;
	private Long profileId;

	@Column(nullable = false)
	private LocalDate createdAt;

	private LocalDate updatedAt;

	@OneToMany(mappedBy = "appUser")
	private List<Phone> phones;

	@OneToOne(mappedBy = "appUser", orphanRemoval = true)
	private Authority authority;

	@OneToOne(mappedBy = "appUser", orphanRemoval = true)
	private Otp otp;

	public AppUser(Long id) {
		this.id = id;
	}

}
