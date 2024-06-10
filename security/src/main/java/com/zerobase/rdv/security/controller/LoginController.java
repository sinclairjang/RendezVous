package com.zerobase.rdv.security.controller;

import com.zerobase.rdv.security.controller.dto.UserCredentials;
import com.zerobase.rdv.security.service.AuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class LoginController {
    private final AuthorizationService loginService;
    public LoginController(AuthorizationService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody
                                      UserCredentials credentials) {
        String jwts = loginService.issueToken(credentials);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                "Bearer " + jwts).header(HttpHeaders.
                        ACCESS_CONTROL_EXPOSE_HEADERS,
                "Authorization").build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody
                                   UserCredentials credentials) {
        loginService.signup(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
