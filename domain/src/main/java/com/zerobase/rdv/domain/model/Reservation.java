package com.zerobase.rdv.domain.model;

import com.zerobase.rdv.domain.type.ReservationResult;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RDV_RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "CUSTOMER_ID"
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "BUSINESS_ID"
    )
    private Business business;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    private ReservationResult reservationResult;

    public Reservation() {};

    public Reservation(
            Customer customer,
            Business business) {
        this.customer = customer;
        this.business = business;
        customer.addReservation(this);
        business.addReservation(this);
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Business getBusiness() {
        return business;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ReservationResult getReservationResult() {
        return reservationResult;
    }

    public void setReservationResult(ReservationResult reservationResult) {
        this.reservationResult = reservationResult;
    }
}
