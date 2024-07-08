package com.jmjung.table_reservation.exception.review;

import com.jmjung.table_reservation.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class InvalidUserForWriteReviewException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "invalid user for writing reivew";
    }
}
