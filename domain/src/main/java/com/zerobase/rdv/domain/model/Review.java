package com.zerobase.rdv.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RDV_REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            unique = true,
            updatable = false
    )
    private Customer writer;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            unique = true,
            updatable = false
    )
    private Reservation reservation;

    private String text;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    public Review() {}
    public Review(Customer writer, Reservation reservation, String text) {
        this.writer = writer;
        this.reservation = reservation;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Customer getWriter() {
        return writer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}
