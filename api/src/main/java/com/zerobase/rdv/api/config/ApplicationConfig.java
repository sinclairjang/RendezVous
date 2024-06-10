package com.zerobase.rdv.api.config;

import com.zerobase.rdv.api.controller.auth.ReviewDeletePermission;
import com.zerobase.rdv.api.controller.auth.ReviewEditPermission;
import com.zerobase.rdv.api.controller.auth.ReviewWritePermission;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class ApplicationConfig {
    private final ReviewWritePermission reviewWritePermission;
    private final ReviewEditPermission reviewEditPermission;
    private final ReviewDeletePermission reviewDeletePermission;


    public ApplicationConfig(ReviewWritePermission reviewWritePermission, ReviewEditPermission reviewEditPermission, ReviewDeletePermission reviewDeletePermission) {
        this.reviewWritePermission = reviewWritePermission;
        this.reviewEditPermission = reviewEditPermission;
        this.reviewDeletePermission = reviewDeletePermission;
    }
}
