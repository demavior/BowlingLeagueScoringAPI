package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.League}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class LeagueDTO {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date registerOpen;
    private Date registerBy;
    private Long scoreBasis = 200L;
    private Long handicapPct = 80L;
    private Long playersPerTeam=2L;
    private Long maxTeamsAllowed;

    // Additional field for teams associated with the league
    private Set<TeamResponseDTO> teams;
    private boolean isMember;
}