package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.League;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LeagueRepository extends JpaRepository<League, Long> {

    /**
     * Finds a league by its name.
     *
     * @param name the name of the league to search for
     * @return an Optional containing the found league, if any
     */
    Optional<League> findByName(String name);

    List<League> findAllByTeamsIn(Set<Team> teams);
}
