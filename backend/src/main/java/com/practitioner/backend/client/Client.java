package com.practitioner.backend.client;

import com.practitioner.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Column(name = "insurance_company")
    private String insuranceCompany;
}
