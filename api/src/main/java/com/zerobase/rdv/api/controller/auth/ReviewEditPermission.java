package com.zerobase.rdv.api.controller.auth;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.model.Review;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.CustomerRepository;
import com.zerobase.rdv.domain.repository.ReviewRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("reviewEditPermission")
public class ReviewEditPermission implements PermissionEvaluator {
    private final ApplicationUserRepository applicationUserRepository;
    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;

    public ReviewEditPermission(ApplicationUserRepository applicationUserRepository,
                                 CustomerRepository customerRepository,
                                 ReviewRepository reviewRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        ApplicationUser applicationUser =
                applicationUserRepository.findByUsername(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Application user not found."));

        Review review =
                reviewRepository.findById((Long) targetId)
                        .orElseThrow(() -> new RuntimeException("Review not found."));

        Customer customer =
                customerRepository.findByApplicationUser(applicationUser)
                        .orElseThrow(() -> new RuntimeException("Customer not found."));

        boolean userIsCustomerAndReviewer = customer.getId()
                .equals(review.getWriter().getId());

        return userIsCustomerAndReviewer;
    }
}
