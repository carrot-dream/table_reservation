package com.jmjung.table_reservation.repository.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "member", catalog = "table_reservation")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    String id;
    String password;
    Long roleIdx;
    Date createdAt = new Date();

    public Member(
            String id,
            String password,
            Long roleIdx
    ) {
        this.id = id;
        this.password = password;
        this.roleIdx = roleIdx;
    }

    @Override
    public String toString() {
        return id + "," + password + "," + roleIdx;
    }
}
