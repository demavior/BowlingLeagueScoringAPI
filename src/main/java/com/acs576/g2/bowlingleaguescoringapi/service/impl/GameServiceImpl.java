package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Game;
import com.acs576.g2.bowlingleaguescoringapi.entity.League;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import com.acs576.g2.bowlingleaguescoringapi.repository.GameRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.LeagueRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.TeamRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserDetailsRepository;
import com.acs576.g2.bowlingleaguescoringapi.service.GameService;
import com.acs576.g2.bowlingleaguescoringapi.utils.GameDtoEntityConvertor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Game service.
 */
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final UserDetailsRepository userDetailsRepository;

    public GameServiceImpl(GameRepository gameRepository, LeagueRepository leagueRepository, TeamRepository teamRepository, UserDetailsRepository userDetailsRepository) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public GameDTO createGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setGameWeek(gameDTO.getGame_week());
        game.setGameDate(gameDTO.getGame_date());
        game.setScore(gameDTO.getScore());
        // Verify and retrieve the League by ID
        Long league_id = gameDTO.getLeague_id();
        if (league_id == null) {
            throw new IllegalArgumentException("It is necessary a league to create a game.");
        }
        Optional<League> league = leagueRepository.findById(league_id);
        if (league.isEmpty()) {
            throw new IllegalArgumentException("The league " + league_id + " was not found.");
        }
        // Set League
        game.setLeague(league.get());

        // Associate teams with the game
        Set<Long> teamIds = gameDTO.getTeamIds();
        if (gameDTO.getTeamIds().size() != 2) {
            throw new IllegalArgumentException("There must be two teams associated with the game.");
        }
        Set<Team> teams = new HashSet<>(teamRepository.findAllById(gameDTO.getTeamIds()));
        if (teams.size() != teamIds.size()) {
            throw new IllegalArgumentException("One or more teams were not found.");
        }
        game.setTeams(teams);

        gameRepository.save(game);
        gameDTO.setId(game.getId());
        return gameDTO;
    }

    @Transactional
    @Override
    public void deleteGame(Long gameId) {

        //Since cascade is set it will delete orphans and other entries
        gameRepository.deleteById(gameId);
    }

    @Transactional
    @Override
    public GameDTO updateGame(Long gameId, GameDTO gameDTO) {
        Optional<Game> existingGameOpt = gameRepository.findById(gameId);
        if (existingGameOpt.isEmpty()) {
            throw new IllegalArgumentException("Game with ID " + gameId + " not found.");
        }
        Game existingGame = existingGameOpt.get();

        // Update the game's fields
        existingGame.setGameWeek(gameDTO.getGame_week());
        existingGame.setGameDate(gameDTO.getGame_date());
        existingGame.setScore(gameDTO.getScore());
        // Similar updates for league and team if needed

        // Save the updated game
        gameRepository.save(existingGame);

        // Update the DTO with the persisted game's ID (if not set) and return it
        gameDTO.setId(existingGame.getId());
        return gameDTO;
    }

    @Override
    public List<GameDTO> findGamesByTeamId(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new IllegalArgumentException("Team with ID " + teamId + " does not exist.");
        }
        List<Game> games = gameRepository.findGamesByTeamId(teamId); // Implement this method in your repository
        return games.stream()
                    .map(GameDtoEntityConvertor::convertToDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> getAllGamesByUser(String email) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.findUserDetailsByEmail(email);
        UserDetails userDetails = userDetailsOptional.orElseThrow(
                () -> new IllegalArgumentException("User " + email + " not found."));

        List<Game> games = gameRepository.findGamesByTeamsIn(userDetails.getPlayer().getTeams());
        return games.stream().map(GameDtoEntityConvertor::convertToDTO).collect(Collectors.toList());
    }

}
