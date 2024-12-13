package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(GameDTO gameDTO);
    void deleteGame(Long id);
    /**
     * Updates an existing game.
     *
     * @param gameId The ID of the game to update
     * @param gameDTO The DTO containing the updated game information
     * @return The updated GameDTO
     */
    GameDTO updateGame(Long gameId, GameDTO gameDTO);

    /**
     * Finds all games associated with a given team ID.
     *
     * @param teamId The ID of the team
     * @return A list of GameDTO objects
     */
    List<GameDTO> findGamesByTeamId(Long teamId);

    List<GameDTO> getAllGamesByUser(String name);
}
