package com.zerobase.rdv.api.controller.auth;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.model.Reservation;
import com.zerobase.rdv.domain.model.Review;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.CustomerRepository;
import com.zerobase.rdv.domain.repository.ReservationRepository;
import com.zerobase.rdv.domain.repository.ReviewRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("reviewWritePermission")
public class ReviewWritePermission implements PermissionEvaluator {
    private final ApplicationUserRepository applicationUserRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReviewWritePermission(ApplicationUserRepository applicationUserRepository,
                                 CustomerRepository customerRepository,
                                 ReservationRepository reservationRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject,
                                 Object permission) {
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

        Customer customer =
                customerRepository.findByApplicationUser(applicationUser)
                        .orElseThrow(() -> new RuntimeException("Customer not found."));

        Reservation reservation =
                reservationRepository.findById((Long) targetId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found."));


        boolean userIsCustomerAndBooker = customer.getReservations().stream()
                .anyMatch(entity -> {
                    return entity.getId().equals(reservation.getId());
                });

        return userIsCustomerAndBooker;
    }
}
