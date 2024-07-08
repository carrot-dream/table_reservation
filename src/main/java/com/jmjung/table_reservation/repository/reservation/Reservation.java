package com.jmjung.table_reservation.repository.reservation;

import com.jmjung.table_reservation.model.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "reservation", catalog = "table_reservation")
@NoArgsConstructor
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    Long restaurantIdx;
    Integer status;
    Date reservationAt;
    Date createdAt;

    public Reservation(
            Long restaurantIdx,
            Date reservationAt
    ) {
        this.reservationAt = reservationAt;
        this.createdAt = new Date();
        this.status = 1;
        this.restaurantIdx = restaurantIdx;
    }

    ReservationStatus reservationStatus() {
        return ReservationStatus.from(this.status);
    }

    public void done() {
        status = ReservationStatus.DONE.getCode();
    }

    public void accept() {
        status = ReservationStatus.ACCEPT.getCode();
    }

    public void cancel() {
        status = ReservationStatus.CANCEL.getCode();
    }

    public Boolean canReserve() {
        var status = reservationStatus();

        var before10 = new Date(new Date().getTime() - 60 * 10 * 1000);

        switch (status) {
            case DONE, CANCEL:
                return true;
            case READY:
                return false;
            case ACCEPT:
                if (reservationAt.before(before10)) {
                    return false;
                } else {
                    return true;
                }
            default:
                return false;
        }
    }
}
