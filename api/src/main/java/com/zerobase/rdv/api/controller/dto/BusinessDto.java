package com.zerobase.rdv.api.controller.dto;

import com.zerobase.rdv.domain.model.Address;

import javax.validation.constraints.NotNull;

public class BusinessDto {
    @NotNull
    private final Long businessId;
    private String businessName;
    private String businessEmail;
    private Address businessAddress;
    private String businessDescription;
    private Long totalAvailability;
    private Long currentAvailability;

    public BusinessDto(Long businessId,
                       String businessName,
                       String businessContact,
                       Address businessAddress,
                       String businessDescription,
                       Long totalAvailability,
                       Long currentAvailability) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessEmail = businessContact;
        this.businessAddress = businessAddress;
        this.businessDescription = businessDescription;
        this.totalAvailability = totalAvailability;
        this.currentAvailability = currentAvailability;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(Address businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public Long getTotalAvailability() {
        return totalAvailability;
    }

    public void setTotalAvailability(Long totalAvailability) {
        this.totalAvailability = totalAvailability;
    }

    public Long getCurrentAvailability() {
        return currentAvailability;
    }

    public void setCurrentAvailability(Long currentAvailability) {
        this.currentAvailability = currentAvailability;
    }
}
