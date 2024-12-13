package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the User entity.
 * Provides an abstraction layer over database operations for User entities.
 */
@Repository
public interface UserCredsRepository extends JpaRepository<UserCredentials, Long> {
    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return an Optional containing the found user, if any
     */
    Optional<UserCredentials> findByEmail(String email);
}
