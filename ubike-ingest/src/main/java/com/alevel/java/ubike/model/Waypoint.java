package com.alevel.java.ubike.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "waypoints")
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double altitude;

    @Column(nullable = false)
    private double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint waypoint = (Waypoint) o;
        return Double.compare(waypoint.altitude, altitude) == 0 && Double.compare(waypoint.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altitude, longitude);
    }

}
