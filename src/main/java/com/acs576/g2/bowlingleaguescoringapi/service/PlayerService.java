package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.dto.request.PlayerRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;

/**
 * The interface Player service.
 */
public interface PlayerService {
    /**
     * Create player player response dto.
     *
     * @param teamDto the team dto
     * @param email   the email
     * @return the player response dto
     */
    PlayerResponseDTO createPlayer(PlayerRequestDTO teamDto, String email);

    /**
     * Gets player by id.
     *
     * @param playerId the player id
     * @return the player by id
     */
    PlayerResponseDTO getPlayerById(Long playerId);

    /**
     * Update player player response dto.
     *
     * @param playerDTO the player dto
     * @param email     the email
     * @return the player response dto
     */
    PlayerResponseDTO updatePlayer(PlayerRequestDTO playerDTO, String email);

    /**
     * Delete player.
     *
     * @param email the email
     */
    void deletePlayer(String email);

    /**
     * Gets player details.
     *
     * @param name the name
     * @return the player details
     */
    PlayerResponseDTO getPlayerDetails(String name);
}
