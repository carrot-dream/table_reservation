package com.jmjung.table_reservation.repository.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "review", catalog = "table_reservation")
@NoArgsConstructor
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    Long restaurantIdx;
    Long memberIdx;
    String content;
    Date createdAt = new Date();

    public Review(
            Long restaurantIdx,
            Long memberIdx,
            String content
    ) {
        this.restaurantIdx = restaurantIdx;
        this.memberIdx = memberIdx;
        this.content = content;
    }

}
