package com.rekahdo.user_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

	private boolean emailVerified;

	private Long profileId;

	@Column(nullable = false)
	private LocalDate createdAt;

	private LocalDate updatedAt;

	@OneToOne(mappedBy = "appUser")
	private Authority authority;

	@OneToOne(mappedBy = "appUser")
	private Phone phone;

	@OneToOne(mappedBy = "appUser", orphanRemoval = true)
	private Otp otp;

	public AppUser(Long id) {
		this.id = id;
	}

}
