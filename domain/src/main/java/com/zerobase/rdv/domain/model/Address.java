package com.zerobase.rdv.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    @Column(length = 5)
    private String zipcode;
    private String city;

    private Double latitude;
    private Double longitude;

    public Address() {
    }

    public Address(String street,
                   String zipcode,
                   String city,
                   Double latitude,
                   Double longitude) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }


    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }
}

