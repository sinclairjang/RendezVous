package com.zerobase.rdv.security.service;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.type.Membership;
import com.zerobase.rdv.security.controller.dto.UserCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserRepository applicationUserRepository;

    public AuthorizationService(JwtService jwtService,
                                AuthenticationManager authenticationManager,
                                ApplicationUserRepository applicationUserRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.applicationUserRepository = applicationUserRepository;
    }

    public String issueToken(UserCredentials userCredentials) {
        UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        userCredentials.getUsername(),
                        userCredentials.getPassword());
        Authentication auth = authenticationManager.authenticate(creds);
        // Generate token
        ApplicationUser applicationUser =
                applicationUserRepository.findByUsername(auth.getName())
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found."));
        // Build response with the generated token
        return jwtService.generateToken(auth.getName(),
                auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void signup(UserCredentials userCredentials) {
        if (applicationUserRepository.findByUsername(userCredentials
                .getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        ApplicationUser applicationUser =
                new ApplicationUser(userCredentials.getUsername(),
                        userCredentials.getPassword(),
                        new HashSet<>(Collections.singleton(Membership.BASIC)));

        applicationUserRepository.save(applicationUser);
    }

    @Transactional
    public void addRoles(UserCredentials userCredentials, Membership... roles) {
        ApplicationUser applicationUser =
                applicationUserRepository.findByUsername(userCredentials.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found."));
        applicationUser.addRoles(roles);

        applicationUserRepository.save(applicationUser);
    }

    @Transactional
    public void removeRole(UserCredentials userCredentials, Membership role) {
        if (role.equals(Membership.BASIC)) {
            throw new RuntimeException("Can't remove 'default' role.");
        }

        ApplicationUser applicationUser =
                applicationUserRepository.findByUsername(userCredentials.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found."));

        applicationUser.removeRole(role);

        applicationUserRepository.save(applicationUser);
    }
}
