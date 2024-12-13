package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameScoreDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.GameScore;

public class GameScoreDtoEntityConvertor {

    public static GameScoreDTO convertToDTO(GameScore gameScore) {
        GameScoreDTO gameScoreDTO = new GameScoreDTO();
        gameScoreDTO.setId(gameScore.getId());
        gameScoreDTO.setGameId(gameScore.getGame().getId());
        gameScoreDTO.setTeamId(gameScore.getTeam().getId());
        gameScoreDTO.setPlayerId(gameScore.getPlayer().getId());
        gameScoreDTO.setG1Score(gameScore.getG1Score());
        gameScoreDTO.setG2Score(gameScore.getG2Score());
        gameScoreDTO.setG3Score(gameScore.getG3Score());
        gameScoreDTO.setHandicap(gameScore.getHandicap());
        //Calculated
        gameScoreDTO.setHcp1Score(gameScoreDTO.getG1Score()+gameScoreDTO.getHandicap());
        gameScoreDTO.setHcp2Score(gameScoreDTO.getG2Score()+gameScoreDTO.getHandicap());
        gameScoreDTO.setHcp3Score(gameScoreDTO.getG3Score()+gameScoreDTO.getHandicap());
        gameScoreDTO.setTotalScore(gameScoreDTO.getHcp1Score()+gameScoreDTO.getHcp2Score()+gameScoreDTO.getHcp3Score());


        return gameScoreDTO;
    }
}
