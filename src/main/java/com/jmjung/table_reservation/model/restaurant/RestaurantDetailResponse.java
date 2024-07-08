package com.jmjung.table_reservation.model.restaurant;

import com.jmjung.table_reservation.model.reservation.ReservationStatus;
import com.jmjung.table_reservation.repository.reservation.Reservation;
import com.jmjung.table_reservation.repository.restaurant.Restaurant;
import com.jmjung.table_reservation.repository.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class RestaurantDetailResponse {
    Long idx;
    Long merchantIdx;
    String name;
    String description;
    String location;
    Date createdAt = new Date();

    ReservationStatus reserveStatus;

    List<Review> reviewList;

    public RestaurantDetailResponse(
        Restaurant restaurant,
        Optional<Reservation> reservation,
        List<Review> reviewList
    ) {
        this.idx = restaurant.getIdx();
        this.merchantIdx = restaurant.getMerchantIdx();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.location = restaurant.getLocation();
        this.createdAt = restaurant.getCreatedAt();
        reservation.ifPresent(res -> {
            var status = res.reservationStatus();
            this.reserveStatus = status;
        });
        this.reviewList = reviewList;
    }
}
