package com.acs576.g2.bowlingleaguescoringapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.Team}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TeamResponseDTO {
    private Long id;
    private String name;
    private Set<TeamPlayer> players; // IDs of Players to be associated

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamPlayer {
        private Long id;
        private String name;
        private String email;
        private Long average;
    }
}
