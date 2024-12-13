package com.acs576.g2.bowlingleaguescoringapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Jwt authentication response.
 * Author @ParthManaktala
 */
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthenticationResponse {
    private final String jwt;
}
