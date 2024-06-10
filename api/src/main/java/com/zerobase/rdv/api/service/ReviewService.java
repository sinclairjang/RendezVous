package com.zerobase.rdv.api.service;

import com.zerobase.rdv.api.controller.dto.ReviewDto;
import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.model.Reservation;
import com.zerobase.rdv.domain.model.Review;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.CustomerRepository;
import com.zerobase.rdv.domain.repository.ReservationRepository;
import com.zerobase.rdv.domain.repository.ReviewRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReviewService(ReviewRepository reviewRepository, ApplicationUserRepository applicationUserRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @PreAuthorize("@reviewWritePermission.hasPermission(#reviewDto.reservationId, 'reservation', ''")
    @Transactional
    public ReviewDto.Out postReview(ReviewDto.In reviewDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();

        ApplicationUser applicationUser =
                applicationUserRepository.findByUsername(auth.getName())
                        .orElseThrow(() -> new RuntimeException("Application user not found."));

        Customer customer =
                customerRepository.findByApplicationUser(applicationUser)
                        .orElseThrow(() -> new RuntimeException("Customer not found."));

        Reservation reservation =
                reservationRepository.findById((Long) reviewDto.getReservationId())
                        .orElseThrow(() -> new RuntimeException("Reservation not found."));

        Review review = new Review(customer, reservation, reviewDto.getText());
        review = reviewRepository.save(review);
        return new ReviewDto.Out(review.getId(), review.getUpdatedOn());
    }

    @PreAuthorize("@reviewEditPermission.hasPermission(#reviewId, 'reservation', ''")
    @Transactional
    public ReviewDto.Out editReview(Long reviewId, String text) {
        Review review =
                reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setText(text);
        return new ReviewDto.Out(review.getId(), review.getUpdatedOn());
    }

    @PreAuthorize("@reviewDeletePermission.hasPermission(#reviewId, 'reservation', ''")
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
