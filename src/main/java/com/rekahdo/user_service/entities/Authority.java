package com.rekahdo.user_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private byte id;

	private Integer role;
	private LocalDate assignedAt;

	@OneToOne
	@JoinColumn(name = "user_id")
	private AppUser appUser;

}
