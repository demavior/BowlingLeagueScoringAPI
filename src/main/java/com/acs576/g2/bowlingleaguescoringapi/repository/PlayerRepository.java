package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.Player;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the Player entity.
 * Provides an abstraction layer over database operations for Player entities.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUserDetails(UserDetails userDetails);

    @Modifying
    @Query("delete from Player where id=:id")
    void deletePlayer(Long id);
}
