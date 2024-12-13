package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.GameScore}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GameScoreDTO implements Serializable {
    private Long id;
    private Long gameId;
    private Long teamId;
    private Long playerId;
    private Long g1Score=0L;
    private Long g2Score=0L;
    private Long g3Score=0L;
    private Long handicap;
    private Long hcp1Score=0L;
    private Long hcp2Score=0L;
    private Long hcp3Score=0L;
    private Long totalScore=0L;
}