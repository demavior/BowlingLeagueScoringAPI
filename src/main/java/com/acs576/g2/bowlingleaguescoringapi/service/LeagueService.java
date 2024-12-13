package com.acs576.g2.bowlingleaguescoringapi.service;


import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.LeagueDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.LeagueRankingResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LeagueService {
    LeagueDTO createLeague(LeagueDTO leagueDTO);

    List<LeagueDTO> getAllLeagues(int page, int size);

    Optional<LeagueDTO> getLeagueById(Long leagueId);

    LeagueDTO updateLeague(Long leagueId, LeagueDTO leagueDTO);

    List<PlayerResponseDTO> getLeaguePlayers(Long league_id);

    void deleteLeague(Long leagueId);
    /**
     * Finds all games associated with a given league ID.
     *
     * @param leagueId The ID of the league
     * @return A list of GameDTO objects
     */
    List<GameDTO> findGamesByLeagueId(Long leagueId);

    LeagueDTO addTeamToLeague(Long leagueId, Long teamId);

    List<LeagueRankingResponseDTO> findRankingsByLeagueId(Long leagueId);

    Set<LeagueDTO> getUserLeagues(String email);
    /**
     * Retrieves all leagues with an indication of whether a specific player is a member of each league.
     *
     * @param playerId The ID of the player to check league membership against.
     * @return A list of {@link LeagueDTO} with membership status included.
     * @throws IllegalArgumentException if the player ID does not exist.
     */
    List<LeagueDTO> getLeaguesWithMembership(Long playerId);
}
