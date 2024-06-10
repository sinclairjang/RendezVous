package com.zerobase.rdv.api.controller;

import com.zerobase.rdv.api.controller.dto.ReviewDto;
import com.zerobase.rdv.api.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/post")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> postReview(
            @RequestBody ReviewDto.In reviewDto) {

        return ResponseEntity.ok(
                reviewService.postReview(reviewDto));
    }

    @PostMapping("/edit/{reviewId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> editReview(
            @PathVariable String reviewId,
            @RequestBody String text) {
        return ResponseEntity.ok(
                reviewService.editReview(Long.parseLong(reviewId), text));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    public ResponseEntity<?> deleteReview(
            @PathVariable String reviewId) {
        reviewService.deleteReview(Long.parseLong(reviewId));
        return ResponseEntity.ok().build();
    }
}
