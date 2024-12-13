package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamGameScoreDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GameScoreService {

    GameScoreDTO registerGameScore(GameScoreDTO gameScoreDTO);
    List<GameScoreDTO> gameScoreByPlayerId(Long playerId);
    List<GameScoreDTO> gameScoreByGameId(Long gameId);
    List<TeamGameScoreDTO> teamScoreByGameId(Long gameId);

    List<GameScoreDTO> updateScoreFromCSV(MultipartFile file) throws IOException;
}
