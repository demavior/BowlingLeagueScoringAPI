package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.GameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamGameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.service.GameScoreService;
import com.acs576.g2.bowlingleaguescoringapi.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * REST Controller for handling user-related requests.
 * This controller provides endpoints for user registration, fetching user details by username, etc.
 *
 * @author Parth Manaktala
 */
@RestController
public class GameController {

    private final GameService gameService;
    private final GameScoreService gameScoreService;

    public GameController(GameService gameService, GameScoreService gameScoreService) {
        this.gameService = gameService;
        this.gameScoreService = gameScoreService;
    }

    /**
     * Creates a new game with the provided information.
     *
     * @param gameDTO the DTO containing team's information
     * @return ResponseEntity containing the registered TeamDTO and the HTTP status
     */
    @PostMapping("/admin/createGame")
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO) {
        return new ResponseEntity<>(gameService.createGame(gameDTO), HttpStatus.CREATED);
    }


    @DeleteMapping("/game/delete/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Deleted Game Successfully");
    }

    /**
     * Updates an existing game with the provided information.
     *
     * @param id      The ID of the game to update
     * @param gameDTO The DTO containing the updated game information
     * @return ResponseEntity containing the updated GameDTO and the HTTP status
     */
    @PutMapping("/game/update/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        GameDTO updatedGameDTO = gameService.updateGame(id, gameDTO);
        return ResponseEntity.ok(updatedGameDTO);
    }

    /**
     * Creates a game score with the provided information.
     *
     * @param gameScoreDTO the DTO containing team score's information
     * @return ResponseEntity containing the registered TeamDTO and the HTTP status
     */
    @PostMapping("/game/registerGameScore")
    public ResponseEntity<GameScoreDTO> registerGameScore(@RequestBody GameScoreDTO gameScoreDTO) {
        return new ResponseEntity<>(gameScoreService.registerGameScore(gameScoreDTO), HttpStatus.CREATED);
    }

    @GetMapping("/game/playerGameScore/player/{playerId}")
    public ResponseEntity<List<GameScoreDTO>> playerGameScoresById(@PathVariable Long playerId) {

        List<GameScoreDTO> gameScores = gameScoreService.gameScoreByPlayerId(playerId);
        return ResponseEntity.ok(gameScores);

    }

    @GetMapping("/game/user")
    public ResponseEntity<List<GameDTO>> userGames(Authentication authentication) {
        return ResponseEntity.ok(gameService.getAllGamesByUser(authentication.getName()));
    }

    @PostMapping("/admin/game/gameScore/upload")
    public ResponseEntity<List<GameScoreDTO>> uploadGameScores(@RequestParam("file") MultipartFile file) throws IOException {

        List<GameScoreDTO> scores = gameScoreService.updateScoreFromCSV(file);
        return ResponseEntity.ok(scores);

    }

    @GetMapping("/game/playerGameScore/game/{gameId}")
    public ResponseEntity<List<GameScoreDTO>> playerGameScoresByGame(@PathVariable Long gameId) {

        List<GameScoreDTO> gameScores = gameScoreService.gameScoreByGameId(gameId);
        return ResponseEntity.ok(gameScores);

    }

    @GetMapping("/game/teamGameScore/game/{gameId}")
    public ResponseEntity<List<TeamGameScoreDTO>> teamGameScoresByGame(@PathVariable Long gameId) {

        List<TeamGameScoreDTO> teamScores = gameScoreService.teamScoreByGameId(gameId);
        return ResponseEntity.ok(teamScores);

    }
}

