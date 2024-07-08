package com.jmjung.table_reservation.model.reservation;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    READY(1, "예약 대기"),
    ACCEPT(2, "예약 수락"),
    CANCEL(3, "예약 취소"),
    DONE(4, "완료")
    ;

    Integer code;
    String description;

    ReservationStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ReservationStatus from(int code) {
        switch (code) {
            case 1:
                return ReservationStatus.READY;
            case 2:
                return ReservationStatus.ACCEPT;
            case 3:
                return ReservationStatus.CANCEL;
            case 4:
                return ReservationStatus.DONE;
            default:
                return null;
        }
    }

}
