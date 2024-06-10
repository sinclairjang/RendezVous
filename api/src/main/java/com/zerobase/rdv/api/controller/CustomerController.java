package com.zerobase.rdv.api.controller;

import com.zerobase.rdv.api.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/find/{businessName}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> findBusiness(@PathVariable String businessName) {
        return ResponseEntity.ok(customerService.findBusiness(businessName));
    }

    @PostMapping("/reserve/{businessId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> makeReservation(@PathVariable Long businessId) {
        customerService.makeReservation(businessId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm/{reservationId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId) {
        customerService.confirmReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}
