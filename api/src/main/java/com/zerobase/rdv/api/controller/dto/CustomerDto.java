package com.zerobase.rdv.api.controller.dto;

import com.zerobase.rdv.domain.model.Address;

public class CustomerDto {
    private String customerName;
    private String customerEmail;
    private Address customerAddress;

    public CustomerDto(String customerName, String customerContact, Address customerAddress) {
        this.customerName = customerName;
        this.customerEmail = customerContact;
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }
}
