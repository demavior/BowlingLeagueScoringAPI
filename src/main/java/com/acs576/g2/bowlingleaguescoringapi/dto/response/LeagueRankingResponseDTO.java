package com.acs576.g2.bowlingleaguescoringapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeagueRankingResponseDTO {

    private Long teamId;
    private String teamName;
    private List<GameScoreResponseDTO> games;
    private long totalScore;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameScoreResponseDTO {
        private Long gameId;
        private Long score;
        private Long playerId;
    }
}
