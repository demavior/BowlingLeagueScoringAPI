package com.acs576.g2.bowlingleaguescoringapi.service;


import com.acs576.g2.bowlingleaguescoringapi.dto.request.TeamRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamResponseDTO;

import java.util.List;

/**
 * The interface Team service.
 */
public interface TeamService {
    /**
     * Create team with players team response dto.
     *
     * @param teamDto the team dto
     * @return the team response dto
     */
    TeamResponseDTO createTeamWithPlayers(TeamRequestDTO teamDto);

    /**
     * Add user to team team response dto.
     *
     * @param teamId the team id
     * @param email  the email
     * @return the team response dto
     */
    TeamResponseDTO addUserToTeam(Long teamId, String email);

    /**
     * Gets all teams.
     *
     * @return the all teams
     */
    List<TeamResponseDTO> getAllTeams();
}
