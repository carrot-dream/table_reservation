package com.jmjung.table_reservation.model.member;

import com.jmjung.table_reservation.repository.user.Member;
import lombok.Getter;

@Getter
public class SignUpRequest {
    String id;
    String password;
    Long roleIdx;

    public Member toEntity(String encodedPassword) {
        return new Member(
                getId(),
                encodedPassword,
                roleIdx
        );
    }
}
