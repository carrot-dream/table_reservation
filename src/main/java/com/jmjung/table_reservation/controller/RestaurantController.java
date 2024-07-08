package com.jmjung.table_reservation.controller;

import com.jmjung.table_reservation.model.restaurant.RestaurantRequest;
import com.jmjung.table_reservation.repository.restaurant.Restaurant;
import com.jmjung.table_reservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant/list")
    ResponseEntity<?> getRestaurantList() {
        List<Restaurant> list = restaurantService.allRestaurants();
        // TODO: 예약 정보 같이
        return ResponseEntity.ok(list);
    }

    // 상세 정보
    @GetMapping("/restaurant/{idx}")
    ResponseEntity getRestaurantList(
            @PathVariable Long idx
    ) {
        // TODO: 리뷰정보 같이
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
