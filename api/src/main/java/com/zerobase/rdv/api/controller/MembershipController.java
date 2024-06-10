package com.zerobase.rdv.api.controller;

import com.zerobase.rdv.api.service.BusinessService;
import com.zerobase.rdv.api.service.MembershipService;
import com.zerobase.rdv.domain.model.Business;
import com.zerobase.rdv.domain.model.Customer;
import com.zerobase.rdv.domain.type.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/membership")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @RequestMapping("/join/Customer")
    public ResponseEntity<?> joinCustomer() {
        membershipService.acquireMembership(Membership.CUSTOMER);
       return ResponseEntity.ok().build();
    }

    @RequestMapping("/join/Business")
    public ResponseEntity<?> joinBusiness() {
        membershipService.acquireMembership(Membership.BUSINESS);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/join/Business/Partner")
    public ResponseEntity<?> joinBusinessPartner() {
        membershipService.acquireMembership(Membership.PARTNER);
        return ResponseEntity.ok().build();
    }
}
