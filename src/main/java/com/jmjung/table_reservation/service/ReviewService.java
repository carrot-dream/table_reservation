package com.jmjung.table_reservation.service;

import com.jmjung.table_reservation.exception.auth.InvalidRoleException;
import com.jmjung.table_reservation.exception.auth.InvalidUserException;
import com.jmjung.table_reservation.exception.restaurant.NotFoundRestaurantException;
import com.jmjung.table_reservation.exception.review.InvalidMerchantException;
import com.jmjung.table_reservation.exception.review.InvalidReviewWriterException;
import com.jmjung.table_reservation.exception.review.NotFoundReviewException;
import com.jmjung.table_reservation.model.review.ReviewRequest;
import com.jmjung.table_reservation.repository.restaurant.RestaurantRepository;
import com.jmjung.table_reservation.repository.review.Review;
import com.jmjung.table_reservation.repository.review.ReviewRepository;
import com.jmjung.table_reservation.repository.role.RoleRepository;
import com.jmjung.table_reservation.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final RestaurantRepository restaurantRepository;

    public Review create(
            String  memberId,
            ReviewRequest request
    ) {
        var user = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidUserException());
        var review = request.createEntity(user.getIdx());
        return reviewRepository.save(review);
    }

    public Review update(
            String  memberId,
            Long reviewidx,
            ReviewRequest request
    ) {
        var user = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidUserException());
        var review = reviewRepository.findById(reviewidx)
                .orElseThrow(() -> new NotFoundReviewException());

        if (review.getMemberIdx() != user.getIdx()) {
            throw new InvalidReviewWriterException();
        }

        var newReview = request.updateEntity(review);
        return reviewRepository.save(newReview);
    }

    public String delete(
            String  memberId,
            Long reviewidx
    ) {
        var user = memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidUserException());

        var review = reviewRepository.findById(reviewidx)
                .orElseThrow(() -> new NotFoundReviewException());

        var roleIdx = user.getRoleIdx();

        var role = roleRepository.findByIdx(roleIdx)
                .orElseThrow(() -> new InvalidRoleException());

        // merchant는 자신의 음식점에 달린 모든 리뷰를 삭제 가능
        if (role.isMerchant()) {

            var restaurantIdx = review.getRestaurantIdx();
            var restaurant = restaurantRepository.findById(restaurantIdx)
                    .orElseThrow(() -> new NotFoundRestaurantException());

            if (restaurant.getMerchantIdx() == user.getIdx()) {
                reviewRepository.delete(review);
            } else {
                // 삭제 불가능 - 에러처리
                throw new InvalidMerchantException();
            }
        } else {
            // 유저는 자신이 작성한 리뷰만 삭제 가능
            if (review.getMemberIdx() != user.getIdx()) {
                throw new InvalidReviewWriterException();
            }
            reviewRepository.delete(review);
        }

        return "success";
    }

}
