package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.LeagueDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.LeagueRankingResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.service.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * REST Controller for handling user-related requests.
 * This controller provides endpoints for user registration, fetching user details by username, etc.
 *
 * @author Owaiz Mohammed
 */
@RestController
public class LeagueController {

    private final LeagueService leagueService;


    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("/leagues/register")
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody LeagueDTO leagueDTO) {
        LeagueDTO createdLeague = leagueService.createLeague(leagueDTO);
        return new ResponseEntity<>(createdLeague, HttpStatus.CREATED);
    }


    @GetMapping("/leagues/details")
    public ResponseEntity<List<LeagueDTO>> getAllLeagues(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        List<LeagueDTO> leagues = leagueService.getAllLeagues(page, size);
        return new ResponseEntity<>(leagues, HttpStatus.OK);
    }

    @GetMapping("leagues/{leagueId}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable Long leagueId) {
        Optional<LeagueDTO> leagueOptional = leagueService.getLeagueById(leagueId);
        return leagueOptional
                .map(leagueDTO -> new ResponseEntity<>(leagueDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("leagues/user")
    public ResponseEntity<Set<LeagueDTO>> getUserLeagues(Authentication authentication) {
        return ResponseEntity.ok(leagueService.getUserLeagues(authentication.getName()));
    }

    @PutMapping("leagues/{leagueId}")
    public ResponseEntity<LeagueDTO> updateLeague(@PathVariable Long leagueId, @RequestBody LeagueDTO leagueDTO) {
        LeagueDTO updatedLeague = leagueService.updateLeague(leagueId, leagueDTO);
        return new ResponseEntity<>(updatedLeague, HttpStatus.OK);
    }

    /**
     * Retrieves league players by league id
     *
     * @param leagueId the league of the players to retrieve information
     * @return ResponseEntity containing found player(s)DTO and the HTTP status
     */
    @GetMapping("/user/league-players/{leagueId}")
    public ResponseEntity<List<PlayerResponseDTO>> getLeaguePlayers(@PathVariable Long leagueId) {
        return new ResponseEntity<>(leagueService.getLeaguePlayers(leagueId), HttpStatus.OK);
    }

    @DeleteMapping("leagues/{leagueId}")
    public ResponseEntity<String> deleteLeague(@PathVariable Long leagueId) {
        try {
            leagueService.deleteLeague(leagueId);
            String successMessage = "League ID " + leagueId + " deleted successfully.";
            return ResponseEntity.ok(successMessage);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    /**
     * Lists all games in a given league.
     *
     * @param leagueId The ID of the league
     * @return ResponseEntity containing a list of GameDTOs and the HTTP status
     */
    @GetMapping("/leagues/{leagueId}/games")
    public ResponseEntity<List<GameDTO>> listGamesByLeague(@PathVariable Long leagueId) {
        try {
            List<GameDTO> games = leagueService.findGamesByLeagueId(leagueId);
            return ResponseEntity.ok(games);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/leagues/addTeam/{leagueId}/{teamId}")
    public ResponseEntity<LeagueDTO> createTeam(@PathVariable Long leagueId, @PathVariable Long teamId) {
        return ResponseEntity.ok(leagueService.addTeamToLeague(leagueId, teamId));
    }

    @GetMapping("leagues/{leagueId}/rankings")
    public ResponseEntity<List<LeagueRankingResponseDTO>> getLeagueRankings(@PathVariable Long leagueId) {
        List<LeagueRankingResponseDTO> rankings = leagueService.findRankingsByLeagueId(leagueId);
        return ResponseEntity.ok(rankings);
    }

    /**
     * Endpoint to retrieve all leagues and check if a specific player is a member of those leagues.
     *
     * @param playerId The ID of the player to check membership for each league.
     * @return A ResponseEntity containing a list of {@link LeagueDTO} with membership details.
     */
    @GetMapping("/leagues/player/{playerId}")
    public ResponseEntity<List<LeagueDTO>> getLeaguesForPlayer(@PathVariable Long playerId) {
        try {
            List<LeagueDTO> leagues = leagueService.getLeaguesWithMembership(playerId);
            return ResponseEntity.status(HttpStatus.OK).body(leagues);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
