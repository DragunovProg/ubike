package com.alevel.java.ubike.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "start_id", nullable = false)
    private Waypoint start;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "finish_id", nullable = false)
    private Waypoint finish;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Waypoint getStart() {
        return start;
    }

    public void setStart(Waypoint start) {
        this.start = start;
    }

    public Waypoint getFinish() {
        return finish;
    }

    public void setFinish(Waypoint finish) {
        this.finish = finish;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }
}
