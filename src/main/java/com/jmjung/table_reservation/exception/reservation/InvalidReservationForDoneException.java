package com.jmjung.table_reservation.exception.reservation;

import com.jmjung.table_reservation.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class InvalidReservationForDoneException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "invalid reservation for done";
    }
}
