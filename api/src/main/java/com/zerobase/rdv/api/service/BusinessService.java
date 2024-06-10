package com.zerobase.rdv.api.service;

import com.zerobase.rdv.api.controller.dto.BusinessDto;
import com.zerobase.rdv.domain.model.Accommodation;
import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Business;
import com.zerobase.rdv.domain.model.Reservation;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.BusinessRepository;
import com.zerobase.rdv.domain.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BusinessService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BusinessRepository businessRepository;
    private final ReservationRepository reservationRepository;

    public BusinessService(ApplicationUserRepository applicationUserRepository, BusinessRepository businessRepository, ReservationRepository reservationRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.businessRepository = businessRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public void registerBusiness(BusinessDto businessDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Optional<ApplicationUser> applicationUser =
                applicationUserRepository.findByUsername(auth.getName());

        if (applicationUser.isEmpty()) {
            throw new RuntimeException("Application user not found.");
        }

        if (businessRepository.findByApplicationUser(applicationUser.get())
                .isPresent()) {
            throw new RuntimeException("Business has already been registered.");
        };

        Business business = makeEntity(businessDto);

        businessRepository.save(business);
    }

    private static Business makeEntity(BusinessDto businessDto) {
        Business business = new Business(
                businessDto.getBusinessName(),
                businessDto.getBusinessEmail(),
                businessDto.getBusinessAddress(),
                businessDto.getBusinessDescription());

        Accommodation accommodation =
                new Accommodation(
                        business,
                        businessDto.getTotalAvailability(),
                        businessDto.getCurrentAvailability());
        business.setAccommodation(accommodation);
        return business;
    }

    public Page<Reservation> getReservations(LocalDateTime from,
                                             LocalDateTime to,
                                             Pageable pageable) {

        return reservationRepository.findByCreatedOnBetween(
                from, to, pageable);
    }
}
