package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamGameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Game;
import com.acs576.g2.bowlingleaguescoringapi.entity.GameScore;
import com.acs576.g2.bowlingleaguescoringapi.entity.Player;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;
import com.acs576.g2.bowlingleaguescoringapi.repository.GameRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.GameScoreRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.PlayerRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.TeamRepository;
import com.acs576.g2.bowlingleaguescoringapi.service.GameScoreService;
import com.acs576.g2.bowlingleaguescoringapi.utils.GameScoreDtoEntityConvertor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameScoreServiceImpl implements GameScoreService {
    private final GameScoreRepository gameScoreRepository;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public GameScoreServiceImpl(GameScoreRepository gameScoreRepository, GameRepository gameRepository,
                                TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.gameScoreRepository = gameScoreRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public GameScoreDTO registerGameScore(GameScoreDTO gamescoreDTO) {
        GameScore gameScore = new GameScore();
        // Verify and retrieve the Game by ID
        Long gameId = gamescoreDTO.getGameId();
        if (gameId == null) {
            throw new IllegalArgumentException("It is necessary a game to register the score.");
        }
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isEmpty()) {
            throw new IllegalArgumentException("The Game " + gameId + " was not found.");
        }
        // Verify and retrieve the Team by ID
        Long teamId = gamescoreDTO.getTeamId();
        if (teamId == null) {
            throw new IllegalArgumentException("It is necessary a team to register the score.");
        }
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isEmpty()) {
            throw new IllegalArgumentException("The Team " + teamId + " was not found.");
        }
        // Verify and retrieve the Player by ID
        Long playerId = gamescoreDTO.getPlayerId();
        if (playerId == null) {
            throw new IllegalArgumentException("It is necessary a player to register the score.");
        }
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isEmpty()) {
            throw new IllegalArgumentException("The Player " + playerId + " was not found.");
        }
        List<GameScore> pastScore = gameScoreRepository.findByPlayerIdAndGameId(playerId, gameId);
        if (!pastScore.isEmpty()) {
            throw new IllegalArgumentException("The Player " + playerId + " already has a score for this game.");
        }
        gameScore.setGame(game.get());
        gameScore.setTeam(team.get());
        gameScore.setPlayer(player.get());
        gameScore.setG1Score(gamescoreDTO.getG1Score());
        gameScore.setG2Score(gamescoreDTO.getG2Score());
        gameScore.setG3Score(gamescoreDTO.getG3Score());
        //Calculate and assign Player Game handicap
        long handicap = (game.get().getLeague().getScoreBasis() - player.get().getAverage()) * game.get().getLeague()
                                                                                                   .getHandicapPct() / 100;
        if (handicap < 0) {
            handicap = 0L;
        }
        gameScore.setHandicap(handicap);
        gameScoreRepository.save(gameScore);
        recalculatePlayerAverage(player.get());

        return GameScoreDtoEntityConvertor.convertToDTO(gameScore);
    }

    public List<GameScoreDTO> gameScoreByPlayerId(Long playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isEmpty()) {
            throw new IllegalArgumentException("Player with ID " + playerId + " not found.");
        }
        Set<GameScore> gameScores = player.get().getGameScores();
        return gameScores.stream()
                         .map(GameScoreDtoEntityConvertor::convertToDTO)
                         .collect(Collectors.toList());

    }

    public List<GameScoreDTO> gameScoreByGameId(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isEmpty()) {
            throw new IllegalArgumentException("Game with ID " + gameId + " not found.");
        }
        Set<GameScore> gameScores = game.get().getGameScores();
        return gameScores.stream()
                         .map(GameScoreDtoEntityConvertor::convertToDTO)
                         .collect(Collectors.toList());

    }

    public List<TeamGameScoreDTO> teamScoreByGameId(Long gameId) {
        List<TeamGameScoreDTO> teamScores = new ArrayList<>();
        Map<Long, TeamGameScoreDTO> teamScoreMap = new HashMap<>();
        List<GameScore> gameScores = gameScoreRepository.findByGameId(gameId);

        for (GameScore gameScore : gameScores) {
            Long teamId = gameScore.getTeam().getId();
            TeamGameScoreDTO teamScoreDTO = teamScoreMap.getOrDefault(teamId, new TeamGameScoreDTO());
            teamScoreDTO.setGameId(gameId);
            teamScoreDTO.setTeamId(teamId);
            teamScoreDTO.setG1Score(teamScoreDTO.getG1Score() + gameScore.getG1Score());
            teamScoreDTO.setG2Score(teamScoreDTO.getG2Score() + gameScore.getG2Score());
            teamScoreDTO.setG3Score(teamScoreDTO.getG3Score() + gameScore.getG3Score());
            teamScoreDTO.setHcp1Score(teamScoreDTO.getHcp1Score() + gameScore.getG1Score() + gameScore.getHandicap());
            teamScoreDTO.setHcp2Score(teamScoreDTO.getHcp2Score() + gameScore.getG2Score() + gameScore.getHandicap());
            teamScoreDTO.setHcp3Score(teamScoreDTO.getHcp3Score() + gameScore.getG3Score() + gameScore.getHandicap());
            teamScoreMap.put(teamId, teamScoreDTO);
        }

        for (TeamGameScoreDTO teamScoreDTO : teamScoreMap.values()) {
            teamScoreDTO.setTotalScore(
                    teamScoreDTO.getHcp1Score() + teamScoreDTO.getHcp2Score() + teamScoreDTO.getHcp3Score());
            teamScores.add(teamScoreDTO);
        }

        return teamScores;
    }


    @Override
    public List<GameScoreDTO> updateScoreFromCSV(MultipartFile file) throws RuntimeException, IOException {
        List<GameScoreDTO> gameScoreDTOResponse = new ArrayList<>();
        try (Reader in = new InputStreamReader(file.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                                                           .setHeader()
                                                           .setSkipHeaderRecord(true)
                                                           .build()
                                                           .parse(in);
            for (CSVRecord record : records) {
                GameScoreDTO dto = new GameScoreDTO(
                        null,
                        Long.parseLong(record.get("gameId")),
                        Long.parseLong(record.get("teamId")),
                        Long.parseLong(record.get("playerId")),
                        Long.parseLong(record.get("g1Score")),
                        Long.parseLong(record.get("g2Score")),
                        Long.parseLong(record.get("g3Score")),
                        Long.parseLong(record.get("handicap")),
                        Long.parseLong(record.get("hcp1Score")),
                        Long.parseLong(record.get("hcp2Score")),
                        Long.parseLong(record.get("hcp3Score")),
                        null
                );
                gameScoreDTOResponse.add(registerGameScore(dto));
            }
        }

        return gameScoreDTOResponse;
    }

    private void recalculatePlayerAverage(Player player){
        Set<GameScore> gameScores = player.getGameScores();
        if (gameScores.isEmpty()) {
            player.setAverage(null);
            playerRepository.save(player);
            return;
        }

        int totalScore = 0;
        int totalGames = 0;

        for (GameScore gameScore : gameScores) {

            if (gameScore.getG1Score() != null) {
                totalScore += gameScore.getG1Score();
                totalGames++;
            }
            if (gameScore.getG2Score() != null) {
                totalScore += gameScore.getG2Score();
                totalGames++;
            }
            if (gameScore.getG3Score() != null) {
                totalScore += gameScore.getG3Score();
                totalGames++;
            }
        }

        Long averageScore = (long) (totalScore / totalGames);
        player.setAverage(averageScore);
        playerRepository.save(player);

    }

}
