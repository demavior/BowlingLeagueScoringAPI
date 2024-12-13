package com.acs576.g2.bowlingleaguescoringapi.jwt;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Jwt authentication controller.
 * Author @ParthManaktala
 */
@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Instantiates a new Jwt authentication controller.
     *
     * @param authenticationManager the authentication manager
     * @param jwtTokenUtil          the jwt token util
     */
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Create authentication token response entity.
     *
     * @param authenticationRequest the authentication request
     * @return the response entity
     */
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest) {
        // Attempt authentication with the provided credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Explicitly set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT for the authenticated user
        final String jwt = jwtTokenUtil.generateToken(authenticationRequest.getEmail());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}

