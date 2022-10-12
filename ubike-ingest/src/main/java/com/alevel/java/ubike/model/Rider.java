package com.alevel.java.ubike.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "riders")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String nickname;

    @OneToMany(mappedBy = "rider")
    private List<Ride> rides = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Ride> getRides() {
        return rides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rider rider = (Rider) o;
        return Objects.equals(nickname, rider.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
