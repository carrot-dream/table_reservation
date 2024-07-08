package com.jmjung.table_reservation.repository.restaurant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "restaurant", catalog = "table_reservation")
@NoArgsConstructor
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    Long merchantIdx;
    String name;
    String description;
    String location;
    Date createdAt = new Date();

    public Restaurant(
            String name,
            String description,
            String location
    ) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.createdAt = new Date();
    }

    public Restaurant(
            String name,
            String description,
            String location,
            Long merchantIdx
    ) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.createdAt = new Date();
        this.merchantIdx = merchantIdx;
    }
}
