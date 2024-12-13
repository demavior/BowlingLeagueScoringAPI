package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Game;

public class GameDtoEntityConvertor {

    public static GameDTO convertToDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setGame_date(game.getGameDate());
        gameDTO.setScore(game.getScore());
        gameDTO.setGame_week(game.getGameWeek());
        if (game.getLeague() != null) {
            gameDTO.setLeague_id(game.getLeague().getId());
        }
        return gameDTO;
    }
}
