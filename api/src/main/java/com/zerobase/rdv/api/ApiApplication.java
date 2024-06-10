package com.zerobase.rdv.api;

import com.zerobase.rdv.domain.model.ApplicationUser;
import com.zerobase.rdv.domain.repository.ApplicationUserRepository;
import com.zerobase.rdv.domain.type.Membership;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.zerobase.rdv.security.service.JwtService;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.zerobase.rdv.domain"})
@EntityScan(basePackages = {"com.zerobase.rdv.domain"})
@ComponentScan(basePackages = {"com.zerobase.rdv"})
public class ApiApplication implements CommandLineRunner {
    private final ApplicationUserRepository applicationUserRepository;

    public ApiApplication(ApplicationUserRepository applicationUserRepository, JwtService jwtService) {
        this.applicationUserRepository = applicationUserRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }


    @Override
    public void run(String... args) throws Exception {
        if (applicationUserRepository.findByUsername("user").isEmpty()) {
            // Id: user, password: user
            applicationUserRepository.save(
                    new ApplicationUser("user",
                            "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue",
                            new HashSet<>(Collections.singleton(Membership.BASIC))));
        }

        if (applicationUserRepository.findByUsername("admin").isEmpty()) {
            // Id: admin, password: admin
            applicationUserRepository.save(
                    new ApplicationUser("admin",
                            "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW",
                            new HashSet<>(Collections.singleton(Membership.BASIC))));
        }
    }
}
