package com.practitioner.backend.availability;

import com.practitioner.backend.practitioner.Practitioner;
import com.practitioner.backend.location.Location;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "is_public")
    private Boolean isPublic = true;

    // getters & setters
}
