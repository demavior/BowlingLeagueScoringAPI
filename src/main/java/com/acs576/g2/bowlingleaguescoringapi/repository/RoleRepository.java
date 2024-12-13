package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the Role entity.
 * Provides an abstraction layer over database operations for Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to search for
     * @return an Optional containing the found role, if any
     */
    Optional<Role> findByName(String roleName);
}
