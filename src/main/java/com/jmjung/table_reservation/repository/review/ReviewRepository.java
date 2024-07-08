package com.jmjung.table_reservation.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRestaurantIdx(Long restaurantIdx);

}
