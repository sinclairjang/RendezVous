package com.zerobase.rdv.domain.repository;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByApplicationUser(ApplicationUser applicationUser);

    List<Business> findAllByBusinessName(String businessName);

}
