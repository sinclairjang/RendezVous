package com.zerobase.rdv.api.service;

import com.zerobase.rdv.api.controller.dto.BusinessDto;
import com.zerobase.rdv.api.controller.dto.CustomerDto;
import com.zerobase.rdv.domain.model.*;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.repository.BusinessRepository;
import com.zerobase.rdv.domain.repository.CustomerRepository;
import com.zerobase.rdv.domain.repository.ReservationRepository;
import com.zerobase.rdv.domain.type.ReservationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final ApplicationUserRepository applicationUserRepository;
    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;
    private final ReservationRepository reservationRepository;

    public CustomerService(ApplicationUserRepository applicationUserRepository,
                           CustomerRepository customerRepository,
                           BusinessRepository businessRepository,
                           ReservationRepository reservationRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.customerRepository = customerRepository;
        this.businessRepository = businessRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<BusinessDto> findBusiness(String businessName) {

        return businessRepository
                .findAllByBusinessName(businessName)
                .stream()
                .map(entity -> {
                    return new BusinessDto(
                            entity.getId(),
                            entity.getBusinessName(),
                            entity.getBusinessEmail(),
                            entity.getBusinessAddress(),
                            entity.getBusinessDescription(),
                            entity.getAccommodation().getTotalAvailability(),
                            entity.getAccommodation().getCurrentAvailability()
                    );
                })
                .collect(Collectors.toList());

    }

    @Transactional
    public void makeReservation(Long businessId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Optional<ApplicationUser> applicationUser =
                applicationUserRepository.findByUsername(auth.getName());

        if (applicationUser.isEmpty()) {
            throw new RuntimeException("Application user not found.");
        }

        Customer customer =
                customerRepository.findByApplicationUser(applicationUser.get())
                        .orElseThrow(() -> new RuntimeException("Customer not found"));

        Business business =
                businessRepository.findById(businessId)
                        .orElseThrow(() -> new RuntimeException("Business not found"));

        // 예약 가능 확인 후 진행
        Accommodation accommodation = business.getAccommodation();
        if (accommodation.getCurrentAvailability().equals(0L)) {
            throw new RuntimeException("Fully booked.");
        }

        Reservation reservation = new Reservation(customer, business);

        accommodation.setCurrentAvailability(Math.max(accommodation.getCurrentAvailability() - 1, 0));
        reservationRepository.save(reservation);
    }

    @Transactional
    public void confirmReservation(Long reservationId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Optional<ApplicationUser> applicationUser =
                applicationUserRepository.findByUsername(auth.getName());

        if (applicationUser.isEmpty()) {
            throw new RuntimeException("Application user not found.");
        }

        Customer customer =
                customerRepository.findByApplicationUser(applicationUser.get())
                        .orElseThrow(() -> new RuntimeException("Customer not found"));

        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("Reservation holder not matched");
        }

        /* 예약 10분전에 도착하여 키오스크를 통해서 방문 확인을 진행 */

        if (LocalDateTime.now().isBefore(reservation.getCreatedOn().minusMinutes(10))) {
            throw new RuntimeException("Arrival too early.");
        }

        if (LocalDateTime.now().isAfter(reservation.getCreatedOn())) {
            reservation.setReservationResult(ReservationResult.TIME_OUT);
            throw new RuntimeException("Arrival too late.");
        }

        reservation.setReservationResult(ReservationResult.CONFIRMED);
    }

    @Transactional
    public void registerCustomer(CustomerDto customerDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Optional<ApplicationUser> applicationUser =
                applicationUserRepository.findByUsername(auth.getName());

        if (applicationUser.isEmpty()) {
            throw new RuntimeException("Application user not found.");
        }

        if (customerRepository.findByApplicationUser(applicationUser.get())
                .isPresent()) {
            throw new RuntimeException("Customer has already been registered.");
        };

        Customer customer = makeEntity(customerDto);

        customerRepository.save(customer);
    }

    private Customer makeEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getCustomerName(),
                customerDto.getCustomerEmail(),
                customerDto.getCustomerAddress());
    }
}
