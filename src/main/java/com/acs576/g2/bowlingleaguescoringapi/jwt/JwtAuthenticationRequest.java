package com.acs576.g2.bowlingleaguescoringapi.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Jwt authentication request.
 * Author @ParthManaktala
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtAuthenticationRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}



