package com.rekahdo.user_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rekahdo.user_service.enums.OTPPurpose;
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

	private Integer purpose;

	private boolean verified;

	private LocalDateTime expireAt;

	@OneToOne
	@JoinColumn(name = "user_id")
	private AppUser appUser;

}
