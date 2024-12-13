package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.request.PlayerRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Player;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import com.acs576.g2.bowlingleaguescoringapi.repository.PlayerRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.TeamRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserDetailsRepository;
import com.acs576.g2.bowlingleaguescoringapi.service.PlayerService;
import com.acs576.g2.bowlingleaguescoringapi.utils.PlayerDtoEntityConvertor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user-related operations.
 * This service handles the business logic for user operations such as registration and retrieval.
 *
 * @author Owaiz Mohammed
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final TeamRepository teamRepository;


    /**
     * Instantiates a new Player service.
     *
     * @param playerRepository      the player repository
     * @param userDetailsRepository the user details repository
     * @param teamRepository        the team repository
     */
    public PlayerServiceImpl(PlayerRepository playerRepository, UserDetailsRepository userDetailsRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public PlayerResponseDTO createPlayer(PlayerRequestDTO playerDTO, String email) {
        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email)
                                                       .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = new Player();
        player.setAverage(playerDTO.getAverage());
        player.setUserDetails(userDetails);
        player = playerRepository.save(player);

        return PlayerDtoEntityConvertor.mapEntityToDto(player);
    }

    @Override
    public PlayerResponseDTO getPlayerDetails(String email) {

        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email)
                                                       .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = playerRepository.findByUserDetails(userDetails)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "Given user (" + email + ") is not a player"));

        return PlayerDtoEntityConvertor.mapEntityToDto(player);
    }

    @Override
    public PlayerResponseDTO getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "Player not found with ID: " + playerId));

        return PlayerDtoEntityConvertor.mapEntityToDto(player);
    }

    @Override
    public PlayerResponseDTO updatePlayer(PlayerRequestDTO playerDTO, String email) {

        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email)
                                                       .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = playerRepository.findByUserDetails(userDetails)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "No player associated with this email"));

        player.setAverage(playerDTO.getAverage());
        player.setUserDetails(userDetails);

        Set<Team> teams = new HashSet<>();
        if (playerDTO.getTeamId() != null) {
            teams = playerDTO.getTeamId().stream()
                             .map(id -> teamRepository.findById(id)
                                                      .orElseThrow(() -> new EntityNotFoundException(
                                                              "Team not found with ID: " + id)))
                             .collect(Collectors.toSet());
        }
        player.setTeams(teams);

        // Save the updated entity
        player = playerRepository.save(player);

        return PlayerDtoEntityConvertor.mapEntityToDto(player);
    }

    @Override
    public void deletePlayer(String email) {
        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email)
                                                       .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = playerRepository.findByUserDetails(userDetails)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "No player associated with his login"));

        // Iterate over teams and remove the player from each team's collection of players
        player.getTeams().forEach(team -> {
            team.getPlayers().remove(player); // Remove the player from each team
            // Optional: If you have logic in Team entity to handle bidirectional syncing, use it here
            teamRepository.save(team); // Save the team to update its state in the database
        });

        // Dismiss the player from all teams (synchronizing the other side of the relationship)
        player.dismissTeams(); // You would need to implement this method in Player

        playerRepository.deleteById(player.getId());
    }


}