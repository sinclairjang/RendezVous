package com.zerobase.rdv.domain.repository;

import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCustomer(Customer customer);

    Page<Reservation> findByCreatedOnBetween(LocalDateTime start,
                                             LocalDateTime end,
                                             Pageable pageable);
}
