package com.acs576.g2.bowlingleaguescoringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a User entity in the database.
 */
@Entity
@Table(name = "bl_user_credential")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Additional fields for password management
    @Transient
    private String currentPassword; // To verify during password change

    @Transient
    private String newPassword; // To set during password change

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "bl_user_credential_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns =
    @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "userCredentials", cascade = CascadeType.ALL)
    @JsonBackReference
    private UserDetails userDetails; // Back reference to UserDetails

}

