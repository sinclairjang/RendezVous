package com.zerobase.rdv.api.service;

import com.zerobase.rdv.domain.type.Membership;
import com.zerobase.rdv.security.controller.dto.UserCredentials;
import com.zerobase.rdv.security.service.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
    private final AuthorizationService authorizationService;

    public MembershipService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void acquireMembership(Membership membership) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        UserCredentials userCredentials = new UserCredentials(
                auth.getName(), null);

        authorizationService.addRoles(userCredentials, membership);
    }
}
