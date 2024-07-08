package com.jmjung.table_reservation.service;

import com.jmjung.table_reservation.exception.auth.InvalidUserException;
import com.jmjung.table_reservation.exception.restaurant.NotFoundRestaurantException;
import com.jmjung.table_reservation.model.restaurant.RestaurantRequest;
import com.jmjung.table_reservation.repository.restaurant.Restaurant;
import com.jmjung.table_reservation.repository.restaurant.RestaurantRepository;
import com.jmjung.table_reservation.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> allRestaurants() {
        // TODO: 예약 정보 찾아서 같이 내려줘야 함. ->
        return restaurantRepository.findAll();
    }

    public Restaurant restaurant(
            Long idx
    ) {
        Restaurant restaurant = restaurantRepository.findById(idx)
                .orElseThrow(() -> new NotFoundRestaurantException());
        return restaurant;
    }

    public Restaurant create(
            String username,
            RestaurantRequest request
    ) {
        var member = memberRepository.findById(username)
                .orElseThrow(() -> new InvalidUserException());
        var memberIdx = member.getIdx();
        Restaurant restaurant = request.createEntity(memberIdx);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(
            Long idx,
            RestaurantRequest request
    ) {
        Restaurant restaurant = restaurantRepository.findById(idx)
                .orElseThrow(() -> new NotFoundRestaurantException());
        return request.update(restaurant);
    }

    public String delete(
        Long idx
    ) {
        Restaurant restaurant = restaurantRepository.findById(idx)
                .orElseThrow(() -> new NotFoundRestaurantException());
        restaurantRepository.delete(restaurant);
        return "success";
    }

}
