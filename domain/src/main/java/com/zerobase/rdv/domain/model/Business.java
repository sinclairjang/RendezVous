package com.zerobase.rdv.domain.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RDV_BUSINESS")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String businessEmail;
    private Address businessAddress;
    private String businessDescription;
    @OneToOne(
            mappedBy = "business",
            optional = false,
            cascade = CascadeType.PERSIST
    )
    private Accommodation accommodation;

    @OneToMany(mappedBy = "business")
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(unique = true)
    private ApplicationUser applicationUser;

    public Business(String businessName,
             String businessEmail,
             Address businessAddress,
             String businessDescription) {
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.businessAddress = businessAddress;
        this.businessDescription = businessDescription;
    }

    public Long getId() {
        return id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
