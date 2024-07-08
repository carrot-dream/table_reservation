package com.jmjung.table_reservation.model.review;

import com.jmjung.table_reservation.repository.review.Review;
import lombok.Getter;

@Getter
public class ReviewRequest {
    Long restaurantIdx;
    String content;

    public Review createEntity(Long memberIdx) {
        return new Review(
                memberIdx,
                restaurantIdx,
                content
        );
    }

    public Review updateEntity(Review review) {
        if (!content.isEmpty() && !content.isBlank()) {
            review.setContent(content);
        }
        return review;
    }
}
