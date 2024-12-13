package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.LeagueDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.LeagueRankingResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.*;
import com.acs576.g2.bowlingleaguescoringapi.repository.*;
import com.acs576.g2.bowlingleaguescoringapi.service.LeagueService;
import com.acs576.g2.bowlingleaguescoringapi.utils.GameDtoEntityConvertor;
import com.acs576.g2.bowlingleaguescoringapi.utils.LeagueDtoEntityConverter;
import com.acs576.g2.bowlingleaguescoringapi.utils.PlayerDtoEntityConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;
    private final GameRepository gameRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final TeamRepository teamRepository;
    private final GameScoreRepository gameScoreRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public LeagueServiceImpl(LeagueRepository leagueRepository, GameRepository gameRepository, TeamRepository teamRepository, GameScoreRepository gameScoreRepository, PlayerRepository playerRepository, UserDetailsRepository userDetailsRepository) {
        this.leagueRepository = leagueRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.gameScoreRepository = gameScoreRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public LeagueDTO createLeague(LeagueDTO leagueDTO) {
        // Validating required fields (name and description)
        if (StringUtils.isEmpty(leagueDTO.getName()) && StringUtils.isEmpty(leagueDTO.getDescription())) {
            throw new IllegalArgumentException("League name and description are required.");
        } else if (StringUtils.isEmpty(leagueDTO.getName())) {
            throw new IllegalArgumentException("League name is required.");
        } else if (StringUtils.isEmpty(leagueDTO.getDescription())) {
            throw new IllegalArgumentException("League description is required.");
        }

        // Checking if dates are empty
        if (leagueDTO.getStartDate() == null || leagueDTO.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date are required.");
        }

        // Validating dates
        if (leagueDTO.getStartDate().after(leagueDTO.getEndDate())) {
            throw new IllegalArgumentException("Invalid dates. Start date must be before end date.");
        }

        // Checking if league name is unique
        Optional<League> existingLeague = leagueRepository.findByName(leagueDTO.getName());
        if (existingLeague.isPresent()) {
            throw new IllegalArgumentException("League name already exist, please enter a different one");
        }

        // Creating new League details
        League newLeague = new League();
        newLeague.setName(leagueDTO.getName());
        newLeague.setDescription(leagueDTO.getDescription());
        newLeague.setStartDate(leagueDTO.getStartDate());
        newLeague.setEndDate(leagueDTO.getEndDate());
        newLeague.setRegisterOpen(leagueDTO.getRegisterOpen());
        newLeague.setRegisterBy(leagueDTO.getRegisterBy());
        newLeague.setScoreBasis(leagueDTO.getScoreBasis());
        newLeague.setHandicapPct(leagueDTO.getHandicapPct());

        // Saving new league details
        League savedLeague = leagueRepository.save(newLeague);
        return LeagueDtoEntityConverter.convertEntityToDTO(savedLeague);
    }

    @Override
    public List<LeagueDTO> getAllLeagues(int page, int size) {
        Page<League> leaguePage = leagueRepository.findAll(PageRequest.of(page, size));
        return leaguePage.getContent().stream()
                         .map(LeagueDtoEntityConverter::convertEntityToDTO)
                         .collect(Collectors.toList());
    }

    @Override
    public Optional<LeagueDTO> getLeagueById(Long leagueId) {
        Optional<League> leagueOptional = leagueRepository.findById(leagueId);
        if (leagueOptional.isEmpty()) {
            throw new IllegalArgumentException("League ID not found");
        }
        return leagueOptional.map(LeagueDtoEntityConverter::convertEntityToDTO);
    }

    @Override
    public LeagueDTO updateLeague(Long leagueId, LeagueDTO leagueDTO) {
        // Retrieve existing league by ID
        Optional<League> existingLeagueOptional = leagueRepository.findById(leagueId);
        if (existingLeagueOptional.isEmpty()) {
            throw new IllegalArgumentException("League ID not found");
        }
        League existingLeague = existingLeagueOptional.get();

        // Validating league name
        if (leagueDTO.getName() == null || leagueDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("League name is required.");
        }

        // Checking if updated league name is unique
        if (!leagueDTO.getName().equals(existingLeague.getName())) {
            Optional<League> existingLeagueWithNewName = leagueRepository.findByName(leagueDTO.getName());
            if (existingLeagueWithNewName.isPresent()) {
                throw new IllegalArgumentException("League name already exists, enter a different one");
            }
            existingLeague.setName(leagueDTO.getName());
        }

        // Validating dates
        if (leagueDTO.getStartDate() == null || leagueDTO.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date are required.");
        }

        if (leagueDTO.getStartDate().after(leagueDTO.getEndDate())) {
            throw new IllegalArgumentException("Invalid dates. Start date cannot be after end date.");
        }

        // Updating league details
        if (leagueDTO.getDescription() != null) {
            existingLeague.setDescription(leagueDTO.getDescription());
        }
        if (leagueDTO.getStartDate() != null) {
            existingLeague.setStartDate(leagueDTO.getStartDate());
        }
        if (leagueDTO.getEndDate() != null) {
            existingLeague.setEndDate(leagueDTO.getEndDate());
        }
        if (leagueDTO.getRegisterOpen() != null) {
            existingLeague.setRegisterOpen(leagueDTO.getRegisterOpen());
        }
        if (leagueDTO.getRegisterBy() != null) {
            existingLeague.setRegisterBy(leagueDTO.getRegisterBy());
        }
        if (leagueDTO.getScoreBasis() != null) {
            existingLeague.setScoreBasis(leagueDTO.getScoreBasis());
        }
        if (leagueDTO.getHandicapPct() != null) {
            existingLeague.setHandicapPct(leagueDTO.getHandicapPct());
        }

        // Saving the updated league details
        League updatedLeague = leagueRepository.save(existingLeague);
        return LeagueDtoEntityConverter.convertEntityToDTO(updatedLeague);
    }

    @Override
    public void deleteLeague(Long leagueId) {
        Optional<League> leagueOptional = leagueRepository.findById(leagueId);
        if (leagueOptional.isEmpty()) {
            throw new IllegalArgumentException("League not found with ID: " + leagueId);
        }

        League leagueToDelete = leagueOptional.get();

        // Check if there are associated teams
        if (!leagueToDelete.getTeams().isEmpty()) {
            throw new DataIntegrityViolationException(
                    "League cannot be deleted as there are teams associated with it.");
        }

        leagueRepository.delete(leagueToDelete);
    }


    @Override
    public List<PlayerResponseDTO> getLeaguePlayers(Long leagueId) {
        Optional<League> leagueOptional = leagueRepository.findById(leagueId);
        League league = leagueOptional.orElseThrow(
                () -> new IllegalArgumentException("League not found with ID: " + leagueId));

        Set<Player> players = new HashSet<>();

        // Iterate over each game and collect players from all teams
        for (Game game : league.getGames()) {
            for (Team team : game.getTeams()) {
                players.addAll(team.getPlayers());
            }
        }

        // Convert Players to PlayerDTOs
        return players.stream()
                      .map(PlayerDtoEntityConvertor::mapEntityToDto)
                      .collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> findGamesByLeagueId(Long leagueId) {
        Optional<League> leagueOpt = leagueRepository.findById(leagueId);
        if (leagueOpt.isEmpty()) {
            throw new IllegalArgumentException("League with ID " + leagueId + " not found.");
        }
        Set<Game> games = leagueOpt.get().getGames();
        return games.stream()
                    .map(GameDtoEntityConvertor::convertToDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public LeagueDTO addTeamToLeague(Long leagueId, Long teamId) {
        Optional<League> leagueOpt = leagueRepository.findById(leagueId);
        League league = leagueOpt.orElseThrow(
                () -> new IllegalArgumentException("League with ID " + leagueId + " not found."));

        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Team team = teamOptional.orElseThrow(
                () -> new IllegalArgumentException("Team with ID " + teamId + " not found."));

        league.getTeams().add(team);
        team.setLeague(league);

        return LeagueDtoEntityConverter.convertEntityToDTO(leagueRepository.save(league));
    }

    @Override
    public List<LeagueRankingResponseDTO> findRankingsByLeagueId(Long leagueId) {
        League league = leagueRepository.findById(leagueId).orElse(null);
        if (league == null) {
            return new ArrayList<>();
        }

        List<LeagueRankingResponseDTO> rankings = new ArrayList<>();

        //Find all games by team in a league
        Map<Long, List<Long>> teamGameMap = league.getTeams().stream()
                                                  .collect(Collectors.toMap(
                                                          Team::getId,
                                                          team -> team.getGames()
                                                                      .stream()
                                                                      .map(Game::getId)
                                                                      .collect(Collectors.toList())
                                                  ));

        Map<Long, String> teamNameMap = league.getTeams().stream()
                                              .collect(Collectors.toMap(
                                                      Team::getId,
                                                      Team::getName
                                              ));


        teamGameMap.forEach((teamId, gamesIds) -> {
            LeagueRankingResponseDTO leagueRankingResponseDTO = new LeagueRankingResponseDTO();
            leagueRankingResponseDTO.setTeamId(teamId);
            leagueRankingResponseDTO.setTeamName(teamNameMap.get(teamId));
            leagueRankingResponseDTO.setGames(new ArrayList<>());

            AtomicLong totalGamesScore = new AtomicLong(0L);
            gamesIds.forEach(gameId -> {
                List<GameScore> gameScores = gameScoreRepository.findAllByTeamIdAndGameId(teamId, gameId);
                gameScores.forEach(gameScore -> {
                    LeagueRankingResponseDTO.GameScoreResponseDTO gameScoreResponseDTO = new LeagueRankingResponseDTO.GameScoreResponseDTO();
                    gameScoreResponseDTO.setGameId(gameId);

                    long individualGameScore = gameScore.getG1Score() + gameScore.getG2Score() + gameScore.getG3Score() + 3 * gameScore.getHandicap();

                    totalGamesScore.addAndGet(individualGameScore);
                    gameScoreResponseDTO.setScore(individualGameScore);
                    gameScoreResponseDTO.setPlayerId(gameScore.getPlayer().getId());

                    leagueRankingResponseDTO.getGames().add(gameScoreResponseDTO);
                });
            });

            leagueRankingResponseDTO.setTotalScore(totalGamesScore.longValue());
            rankings.add(leagueRankingResponseDTO);
        });

        return rankings;
    }

    @Override
    public Set<LeagueDTO> getUserLeagues(String email) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.findUserDetailsByEmail(email);

        UserDetails userDetails = userDetailsOptional.orElseThrow(
                () -> new IllegalArgumentException("User details not found."));

        Set<Team> teams = userDetails.getPlayer().getTeams();

        List<League> leagues = leagueRepository.findAllByTeamsIn(teams);

        return leagues.stream().map(LeagueDtoEntityConverter::convertEntityToDTO).collect(Collectors.toSet());

    }

    public List<LeagueDTO> getLeaguesWithMembership(Long playerId) {
        List<League> allLeagues = leagueRepository.findAll();
        Set<Long> playerLeagueIds = playerRepository.findById(playerId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Player not found"))
                                                    .getTeams()
                                                    .stream()
                                                    .map(team -> team.getLeague().getId())
                                                    .collect(Collectors.toSet());

        return allLeagues.stream().map(league -> {
            LeagueDTO dto = LeagueDtoEntityConverter.convertEntityToDTO(league);
            dto.setMember(playerLeagueIds.contains(league.getId()));
            return dto;
        }).collect(Collectors.toList());
    }


}

