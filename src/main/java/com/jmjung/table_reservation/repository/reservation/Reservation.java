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
    Long memberIdx;
    Integer status;
    Date reservationAt;
    Date createdAt;

    public Reservation(
            Long restaurantIdx,
            Long memberIdx,
            Date reservationAt
    ) {
        this.reservationAt = reservationAt;
        this.memberIdx = memberIdx;
        this.createdAt = new Date();
        this.status = 1;
        this.restaurantIdx = restaurantIdx;
    }

    public ReservationStatus reservationStatus() {
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

    public Boolean canWriteReview() {
        return reservationStatus() == ReservationStatus.DONE;
    }

    public Boolean canDone() {
        var status = reservationStatus();
        var before10 = new Date(new Date().getTime() - 60 * 10 * 1000);
        return reservationAt.before(before10) && status == ReservationStatus.ACCEPT;
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
