package com.zerobase.rdv.domain.model;

import com.zerobase.rdv.domain.type.Membership;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "RDV_APPLICATION_USER")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    @ElementCollection(targetClass = Membership.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Membership> roles = new HashSet<>();

    public ApplicationUser() {}

    public ApplicationUser(String username,
                           String password,
                           Set<Membership> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles.stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    public void addRoles(Membership... roles) {
        this.roles.addAll(List.of(roles));
    }

    public void removeRole(Membership role) {
        this.roles.remove(role);
    }
}
