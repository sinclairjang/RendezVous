package com.zerobase.rdv.domain.repository;

import com.zerobase.rdv.domain.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByUpdatedOnBetween(LocalDateTime start,
                                        LocalDateTime end,
                                        Pageable pageable);
}
