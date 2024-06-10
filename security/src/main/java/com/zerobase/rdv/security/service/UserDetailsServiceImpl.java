package com.zerobase.rdv.security.service;

import java.util.Optional;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User.
        UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserRepository repository;

    public UserDetailsServiceImpl(ApplicationUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        Optional<ApplicationUser> user = repository.findByUsername(username);
        UserBuilder builder = null;
        if (user.isPresent()) {
            ApplicationUser currentUser = user.get();
            builder = org.springframework.security.core.userdetails.
                    User.withUsername(username);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRoles().toArray(new String[0]));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}