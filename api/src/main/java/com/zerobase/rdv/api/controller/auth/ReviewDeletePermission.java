package com.zerobase.rdv.api.controller.auth;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.model.Review;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.CustomerRepository;
import com.zerobase.rdv.domain.repository.ReviewRepository;
import com.zerobase.rdv.domain.type.Membership;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component("reviewDeletePermission")
public class ReviewDeletePermission implements PermissionEvaluator {
    private final ApplicationUserRepository applicationUserRepository;
    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;

    public ReviewDeletePermission(ApplicationUserRepository applicationUserRepository,
                                CustomerRepository customerRepository,
                                ReviewRepository reviewRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
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

        Optional<Customer> customer =
                customerRepository.findByApplicationUser(applicationUser);

        boolean userIsCustomerAndReviewer = false;
        if (customer.isPresent()) {
            userIsCustomerAndReviewer = customer.get().getId()
                    .equals(review.getWriter().getId());
        }

        boolean userIsManager =
                authentication.getAuthorities().stream().anyMatch(
                        entity -> {
                            return entity.getAuthority().equals(Membership.MANAGER.toString());
                        }
                );

        return userIsCustomerAndReviewer || userIsManager;
    }
}
