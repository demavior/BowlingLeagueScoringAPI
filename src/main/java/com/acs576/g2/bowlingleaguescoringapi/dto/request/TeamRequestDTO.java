package com.acs576.g2.bowlingleaguescoringapi.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link com.acs576.g2.bowlingleaguescoringapi.entity.Team}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TeamRequestDTO {
    private Long id;
    private String name;
    private Set<Long> playerIds = new HashSet<>(); // IDs of Players to be associated

}