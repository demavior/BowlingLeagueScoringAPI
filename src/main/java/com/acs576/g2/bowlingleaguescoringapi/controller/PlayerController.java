package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.request.PlayerRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The type Player controller.
 */
@RestController
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Instantiates a new Player controller.
     *
     * @param playerService the player service
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Create player response entity.
     *
     * @param playerDTO      the player dto
     * @param authentication the authentication
     * @return the response entity
     */
/*
        Since a user is a player, we extract the userId from the jwt token
     */
    @PostMapping("api/player")
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody PlayerRequestDTO playerDTO, Authentication authentication) {
        PlayerResponseDTO createdPlayer = playerService.createPlayer(playerDTO, authentication.getName());
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    /**
     * Gets player.
     *
     * @param authentication the authentication
     * @return the player
     */
    @GetMapping("/api/player")
    public ResponseEntity<PlayerResponseDTO> getPlayer(Authentication authentication) {
        PlayerResponseDTO player = playerService.getPlayerDetails(authentication.getName());
        return ResponseEntity.ok(player);
    }

    /**
     * Gets player by id.
     *
     * @param id the id
     * @return the player by id
     */
// Get Player API
    @GetMapping("/admin/player/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(@PathVariable Long id) {
        PlayerResponseDTO player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    /**
     * Update player response entity.
     * Since a user is a player, we extract the userId from the jwt token, no need to get player id
     *
     * @param playerDetails  the player details
     * @param authentication the authentication
     * @return the response entity
     */
    @PutMapping("api/player")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@RequestBody PlayerRequestDTO playerDetails, Authentication authentication) {
        PlayerResponseDTO updatedPlayer = playerService.updatePlayer(playerDetails, authentication.getName());
        return ResponseEntity.ok(updatedPlayer);
    }

    /**
     * Delete player response entity.
     * Since a user is a player, we extract the userId from the jwt token, no need to get player id
     *
     * @param authentication the authentication
     * @return the response entity
     */
    @DeleteMapping("api/player")
    public ResponseEntity<Void> deletePlayer(Authentication authentication) {
        playerService.deletePlayer(authentication.getName());
        return ResponseEntity.noContent().build();
    }

}
