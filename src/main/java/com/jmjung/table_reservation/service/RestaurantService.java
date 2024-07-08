package com.jmjung.table_reservation.service;

import com.jmjung.table_reservation.exception.auth.InvalidUserException;
import com.jmjung.table_reservation.exception.restaurant.NotFoundRestaurantException;
import com.jmjung.table_reservation.model.restaurant.RestaurantListItemResponse;
import com.jmjung.table_reservation.model.restaurant.RestaurantRequest;
import com.jmjung.table_reservation.repository.reservation.Reservation;
import com.jmjung.table_reservation.repository.reservation.ReservationRepository;
import com.jmjung.table_reservation.repository.restaurant.Restaurant;
import com.jmjung.table_reservation.repository.restaurant.RestaurantRepository;
import com.jmjung.table_reservation.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    private final ReservationRepository reservationRepository;


    public List<RestaurantListItemResponse> allRestaurants() {
        // TODO: 예약 정보 찾아서 같이 내려줘야 함. ->
        var list = restaurantRepository.findAll();

        var restaurantIdxList = list.stream()
                .map(restaurant -> restaurant.getIdx())
                .collect(Collectors.toList());

        var map = reservationRepository.findAllByRestaurantIdxIn(restaurantIdxList)
                .stream()
                .collect(Collectors.groupingBy(Reservation::getRestaurantIdx));

        return list.stream()
                .map((item) -> {
                    var reservationList = map.get(item.getIdx());
                    Optional<Reservation> reservation;
                    if (reservationList.size() != 0) {
                        var result = reservationList.get(reservationList.size() - 1);
                        reservation = Optional.of(result);
                    } else  {
                        reservation = Optional.empty();
                    }
                    return new RestaurantListItemResponse(
                            item,
                            reservation
                    );
                })
                .collect(Collectors.toList());
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
