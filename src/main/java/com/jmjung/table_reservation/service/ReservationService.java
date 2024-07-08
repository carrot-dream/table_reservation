package com.jmjung.table_reservation.service;

import com.jmjung.table_reservation.exception.auth.InvalidUserException;
import com.jmjung.table_reservation.exception.reservation.InvalidReservationTimeException;
import com.jmjung.table_reservation.exception.reservation.NotFoundReservationException;
import com.jmjung.table_reservation.exception.restaurant.NotFoundRestaurantException;
import com.jmjung.table_reservation.repository.reservation.ReservationRepository;
import com.jmjung.table_reservation.repository.reservation.Reservation;
import com.jmjung.table_reservation.repository.restaurant.RestaurantRepository;
import com.jmjung.table_reservation.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 예약 처리
     * - 음식점의 최근 예약 정보를 가져옴
     * - 없거나 상태가 done, cancel이거나 예약 시간 10분 전이 지났다면 예약 가능
     */
    public Reservation reserve(
            Long restaurantIdx,
            String memberId,
            Date reservationAt
    ) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidUserException());

        if (!reservationAt.after(new Date())) {
            throw new InvalidReservationTimeException();
        }

        var exists = restaurantRepository.existsByIdx(restaurantIdx);
        if (!exists) {
            throw new NotFoundRestaurantException();
        }
        var reservation = reservationRepository
                .findTopByRestaurantIdxOrderByCreatedAtDesc(restaurantIdx);

        if (reservation.isEmpty() || reservation.get().canReserve()) {
            var newReservation = new Reservation(restaurantIdx, member.getIdx(), reservationAt);
            return reservationRepository.save(newReservation);
        } else {
            return reservation.get();
        }
    }

    public List<Reservation> reservationList(
            Long restaurantIdx
    ) {
        var exists = restaurantRepository.existsByIdx(restaurantIdx);
        if (!exists) {
            throw new NotFoundRestaurantException();
        }
        return reservationRepository.findAllByRestaurantIdx(restaurantIdx);
    }

    public Reservation done(Long idx) {
        var reservation = reservationRepository.findById(idx)
                .orElseThrow(() -> new NotFoundReservationException());
        reservation.done();
        return reservationRepository.save(reservation);
    }

    public Reservation cancel(Long idx) {
        var reservation = reservationRepository.findById(idx)
                .orElseThrow(() -> new NotFoundReservationException());
        reservation.cancel();
        return reservationRepository.save(reservation);
    }

    public Reservation accept(Long idx) {
        var reservation = reservationRepository.findById(idx)
                .orElseThrow(() -> new NotFoundReservationException());
        reservation.accept();
        return reservationRepository.save(reservation);
    }

}
