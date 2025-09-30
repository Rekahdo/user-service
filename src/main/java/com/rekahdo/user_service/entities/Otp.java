package com.rekahdo.user_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "otps")
public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer otp;

	private String purpose;

	private String sentTo;

	private boolean verified;

	private LocalDateTime expireAt;

	@OneToOne
	@JoinColumn(name = "user_id")
	private AppUser appUser;

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireAt);
	}
}
