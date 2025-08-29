package com.rekahdo.user_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "phone_numbers")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String number;

    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

}