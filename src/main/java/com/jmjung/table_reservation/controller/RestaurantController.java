package com.jmjung.table_reservation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {

    // 레스토랑 현황 - 모두
    // 상세 정보 - 모두
        // 예약 상태도 함께 내려줘야 함

    // 삭제 - 점장만
    // 수정 - 점장만
    // 생성 - 점장만

    @GetMapping("/restaurant/list")
    String getRestaurantList() {
        return "goood";
    }

    // 상세 정보
    @GetMapping("/restaurant/{idx}")
    String getRestaurantList(
            @PathVariable Long idx
    ) {
        return "goood";
    }

    @PostMapping("/restaurant")
    @PreAuthorize("hasRole('MERCHANT')")
    String createRestaurant() {
        return "";
    }

    @PutMapping("/restaurant")
    @PreAuthorize("hasRole('MERCHANT')")
    String updateRestaurant() {
        return "";
    }

    @DeleteMapping("/restaurant")
    @PreAuthorize("hasRole('MERCHANT')")
    String deleteRestaurant() {
        return "";
    }

}
