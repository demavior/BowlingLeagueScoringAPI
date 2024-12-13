package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the Team entity.
 * Provides an abstraction layer over database operations for Team entities.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}