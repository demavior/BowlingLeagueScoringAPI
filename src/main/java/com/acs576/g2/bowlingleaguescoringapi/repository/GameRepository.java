package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.Game;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByLeagueId(Long leagueId);

    @Query("SELECT g FROM Game g JOIN g.teams t WHERE t.id = :teamId")
    List<Game> findGamesByTeamId(@Param("teamId") Long teamId);

    List<Game> findGamesByTeamsIn(Set<Team> teams);
}
