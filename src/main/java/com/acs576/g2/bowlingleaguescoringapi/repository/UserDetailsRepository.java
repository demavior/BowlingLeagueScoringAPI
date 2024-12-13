package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the User entity.
 * Provides an abstraction layer over database operations for User entities.
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    @Query("SELECT ud FROM UserDetails ud WHERE ud.userCredentials.email = :email")
    Optional<UserDetails> findUserDetailsByEmail(String email);

    Optional<UserDetails> findUserDetailsByUserCredentials(UserCredentials userCredentials);

}
