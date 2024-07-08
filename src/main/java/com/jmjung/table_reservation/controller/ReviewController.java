package com.jmjung.table_reservation.controller;

import com.jmjung.table_reservation.model.review.ReviewRequest;
import com.jmjung.table_reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * - 예약 후 사용까지 완료 하여야 작성 가능
     */
    @PostMapping("/review")
    ResponseEntity create(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody ReviewRequest request
    ) {
        var name = userDetails.getUsername();
        var result = reviewService.create(name, request);
        return ResponseEntity.ok(result);
    }

    /**
     * 리뷰 수정
     * - 작성자만 수정 가능
     * - 본인이 아닌 경우 예외
     */
    @PutMapping("/review/{reviewIdx}")
    ResponseEntity update(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ReviewRequest request,
            @PathVariable Long reviewIdx
    ) {
        var memberId = userDetails.getUsername();
        var result = reviewService.update(memberId, reviewIdx, request);
        return ResponseEntity.ok(result);
    }

    /**
     * 리뷰 삭제
     * - 모두 수정, 삭제 가능
     */
    @DeleteMapping("/review/")
    ResponseEntity delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long reviewIdx
    ) {
        var memberId = userDetails.getUsername();
        var result = reviewService.delete(memberId, reviewIdx);
        return ResponseEntity.ok(result);
    }

}
