package com.alevel.java.ubike.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Waypoint location;

    @OneToMany(mappedBy = "vehicle")
    private List<Ride> rides = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Waypoint getLocation() {
        return location;
    }

    public void setLocation(Waypoint location) {
        this.location = location;
    }

    public List<Ride> getRides() {
        return rides;
    }
}
