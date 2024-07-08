package com.jmjung.table_reservation.repository.reservation;

import com.jmjung.table_reservation.repository.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findTopByRestaurantIdxOrderByCreatedAtDesc(Long restaurantIdx);
    List<Reservation> findAllByRestaurantIdx(Long restaurantIdx);

    List<Reservation> findAllByRestaurantIdxIn(List<Long> restaurantIdxList);
}
