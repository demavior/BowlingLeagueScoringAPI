package com.acs576.g2.bowlingleaguescoringapi.dto.request;

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
public class PlayerRequestDTO implements Serializable {
    private Long average;
    private Set<Long> teamId;
}