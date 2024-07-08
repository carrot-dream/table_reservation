package com.jmjung.table_reservation.controller;

import com.jmjung.table_reservation.model.reservation.ReservationReserveRequest;
import com.jmjung.table_reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약
     * - 음식점 idx를 받아서 예약 처리
     * - 예외
     *  - 가장 최근 예약이 done 또는 null 상태 또는 예약 시간 10분 전이 지난 경우 예약 처리
     *  - 현재 시간보다 이전시간 예약 시도하는 경우 에러
     */
    @PostMapping("/reservation/{restaurantIdx}")
    ResponseEntity reserve(
            @PathVariable Long restaurantIdx,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ReservationReserveRequest request
    ) {
        var memberId = userDetails.getUsername();
        var reservation = reservationService
                .reserve(restaurantIdx, memberId, request.getReservationAt());
        return ResponseEntity.ok(reservation);
    }

    /**
     * 예약 현황 확인
     *  - 점장만 확인 가능
     *  - 특정 음식점에 대한 에약 히스토리, 현황 확인
     */
    @GetMapping("/reservation/list/{restaurantIdx}")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity reservationList(
            @PathVariable Long restaurantIdx
    ) {
        var list = reservationService.reservationList(restaurantIdx);
        return ResponseEntity.ok(list);
    }

    /**
     * 예약 완료
     * - 키오스크에서 방문 확인시 점장 계정으로 완료 요청
     */
    @PutMapping("/reservation/done/{idx}")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity done(
            @PathVariable Long idx
    ) {
        var result = reservationService.done(idx);
        return ResponseEntity.ok(result);
    }

    /**
     * 예약 취소
     * - 점장이 예약 현황을 확인하여 취소 처리
     */
    @PutMapping("/reservation/cancel/{idx}")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity cancel(
            @PathVariable Long idx
    ) {
        var result = reservationService.cancel(idx);
        return ResponseEntity.ok(result);
    }

    /**
     * 예약 수락
     * - 점장이 예약 현황을 확인하여 수락 처리
     */
    @PutMapping("/reservation/accept/{idx}")
    @PreAuthorize("hasRole('MERCHANT')")
    ResponseEntity accept(
            @PathVariable Long idx
    ) {
        var result = reservationService.accept(idx);
        return ResponseEntity.ok(result);
    }


}
