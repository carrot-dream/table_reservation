package com.jmjung.table_reservation.repository.restaurant;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant", catalog = "table_reservation")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
