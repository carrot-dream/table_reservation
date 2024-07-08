package com.jmjung.table_reservation.controller;

import com.jmjung.table_reservation.model.restaurant.RestaurantRequest;
import com.jmjung.table_reservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    /**
     * 음식점 리스트 확인
     * - 예약 상태도 확인 가능
     */
    @GetMapping("/restaurant/list")
    ResponseEntity<?> getRestaurantList() {
        var list = restaurantService.allRestaurants();
        return ResponseEntity.ok(list);
    }

    /**
     * 음식점 정보 상세
     * - 리뷰도 함께 전송
     */
    @GetMapping("/restaurant/{idx}")
    ResponseEntity getRestaurantList(
            @PathVariable Long idx
    ) {
        var restaurant = restaurantService.restaurant(idx);
        return ResponseEntity.ok(restaurant);
    }

    /**
     * 음식점 생성
     */
    @PostMapping("/restaurant")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity createRestaurant(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RestaurantRequest request
    ) {
        var username = userDetails.getUsername();
        var restaurant = restaurantService.create(username, request);
        return ResponseEntity.ok(restaurant);
    }

    /**
     * 음식점 수정
     */
    @PutMapping("/restaurant/{idx}")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity updateRestaurant(
            @PathVariable Long idx,
            @RequestBody RestaurantRequest request
            ) {
        var restaurant = restaurantService.update(idx, request);
        return ResponseEntity.ok(restaurant);
    }

    /**
     * 음식점 삭제
     */
    @DeleteMapping("/restaurant/{idx}")
    @PreAuthorize("hasRole('MERCHANT')")
    String deleteRestaurant(
        @PathVariable Long idx
    ) {
        return restaurantService.delete(idx);
    }

}
