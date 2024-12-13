package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.request.TeamRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.service.GameService;
import com.acs576.g2.bowlingleaguescoringapi.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller for handling user-related requests.
 * This controller provides endpoints for user registration, fetching user details by username, etc.
 *
 * @author Owaiz Mohammed
 */
@RestController
public class TeamController {

    private final TeamService teamService;
    private final GameService gameService;

    /**
     * Instantiates a new User controller.
     *
     * @param teamService the user service
     */
    public TeamController(TeamService teamService, GameService gameService) {
        this.teamService = teamService;
        this.gameService = gameService;
    }

    /**
     * Registers a new team with the provided information.
     *
     * @param teamDTO the DTO containing user registration information
     * @return ResponseEntity containing the registered TeamDTO and the HTTP status
     */
    @PostMapping("/admin/registerTeam")
    public ResponseEntity<TeamResponseDTO> registerTeam(@RequestBody TeamRequestDTO teamDTO) {
        return new ResponseEntity<>(teamService.createTeamWithPlayers(teamDTO), HttpStatus.CREATED);
    }

    /**
     * Add current user to team response entity.
     *
     * @param teamId         the team id
     * @param authentication the authentication
     * @return the response entity
     */
    @PostMapping("api/team/{teamId}")
    public ResponseEntity<TeamResponseDTO> addCurrentUserToTeam(@PathVariable Long teamId, Authentication authentication) {
        return ResponseEntity.ok(teamService.addUserToTeam(teamId, authentication.getName()));
    }

    /**
     * Gets all teams.
     *
     * @return the all teams
     */
    @GetMapping("api/team/all")
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    /**
     * Lists all games for a given team.
     *
     * @param teamId The ID of the team
     * @return ResponseEntity containing a list of GameDTOs and the HTTP status
     */
    @GetMapping("/teams/{teamId}/games")
    public ResponseEntity<List<GameDTO>> listGamesByTeam(@PathVariable Long teamId) {
        try {
            List<GameDTO> games = gameService.findGamesByTeamId(teamId);
            return ResponseEntity.ok(games);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

