package com.acs576.g2.bowlingleaguescoringapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.Player}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PlayerResponseDTO implements Serializable {
    private Long playerId;
    private Long userId;
    private String name;
    private Long average;
    private Set<PlayerTeams> teams;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlayerTeams {
        private Long teamId;
        private String teamName;
    }
}