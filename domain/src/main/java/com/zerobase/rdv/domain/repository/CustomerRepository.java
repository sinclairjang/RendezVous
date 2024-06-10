package com.zerobase.rdv.domain.repository;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByApplicationUser(ApplicationUser applicationUser);
}
