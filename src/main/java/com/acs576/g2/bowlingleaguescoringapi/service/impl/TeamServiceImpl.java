package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.request.TeamRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Player;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import com.acs576.g2.bowlingleaguescoringapi.repository.LeagueRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.PlayerRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.TeamRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserDetailsRepository;
import com.acs576.g2.bowlingleaguescoringapi.service.TeamService;
import com.acs576.g2.bowlingleaguescoringapi.utils.TeamDtoEntityConvertor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user-related operations.
 * This service handles the business logic for user operations such as registration and retrieval.
 *
 * @author Owaiz Mohammed
 */
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    private final UserDetailsRepository userDetailsRepository;
    private final LeagueRepository leagueRepository;


    /**
     * Instantiates a new Team service.
     *
     * @param teamRepository        the team repository
     * @param playerRepository      the player repository
     * @param userDetailsRepository the user details repository
     */
    public TeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository, UserDetailsRepository userDetailsRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.leagueRepository = leagueRepository;
    }

    /**
     * Create team with players team.
     *
     * @param teamDTO the team dto
     * @return the team
     */
    public TeamResponseDTO createTeamWithPlayers(TeamRequestDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());

        if (teamDTO.getPlayerIds().size() != 2){
            throw new IllegalArgumentException("The team should have 2 players.");
        }

        Set<Player> players = teamDTO.getPlayerIds().stream()
                                     .map(id -> playerRepository.findById(id).orElseThrow(
                                             () -> new RuntimeException("Player not found")))
                                     .collect(Collectors.toSet());

        team.setPlayers(players);
        Team finalTeam = team;
        players.forEach(player -> player.getTeams().add(finalTeam));

        team = teamRepository.save(finalTeam);
        return TeamDtoEntityConvertor.entityToResponseDTO(team);
    }

    @Override
    public TeamResponseDTO addUserToTeam(Long teamId, String email) {
        Team team = teamRepository.findById(teamId)
                                  .orElseThrow(() -> new EntityNotFoundException("Team with given id not found"));

        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email)
                                                       .orElseThrow(
                                                               () -> new EntityNotFoundException("User not found"));

        Player player = playerRepository.findByUserDetails(userDetails)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "No player associated with this email"));

        player.getTeams().add(team);
        team.getPlayers().add(player);

        team = teamRepository.save(team);

        return TeamDtoEntityConvertor.entityToResponseDTO(team);
    }

    @Override
    public List<TeamResponseDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamDtoEntityConvertor::entityToResponseDTO).collect(Collectors.toList());
    }
}