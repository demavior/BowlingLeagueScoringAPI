package com.acs576.g2.bowlingleaguescoringapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a User entity in the database.
 */
@Entity
@Table(name = "bl_user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_credentials_id")
    private UserCredentials userCredentials;

    private String firstName;
    private String lastName;
    private String middleName;

    private Short age;

    @OneToOne(mappedBy = "userDetails", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Player player;

}

