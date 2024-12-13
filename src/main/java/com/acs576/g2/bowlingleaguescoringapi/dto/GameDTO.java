package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.Game}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GameDTO implements Serializable {
    private Long id;
    private Date game_date;
    private Long game_week;
    private String score;
    private Long league_id;
    private Set<Long> teamIds;
}