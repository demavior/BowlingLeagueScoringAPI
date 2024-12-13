package com.acs576.g2.bowlingleaguescoringapi.jwt;

import com.acs576.g2.bowlingleaguescoringapi.dto.ErrorDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The type Jwt authentication filter.
 * Author @ParthManaktala
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService userDetailsService;

    private final JwtTokenUtil jwtUtil;

    /**
     * Instantiates a new Jwt authentication filter.
     *
     * @param userDetailsService the user details service
     * @param jwtUtil            the jwt util
     */
    public JwtAuthenticationFilter(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (IllegalArgumentException e) {
                writeErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid JWT token format.", e.getMessage());
                return; // Stop further processing
            } catch (ExpiredJwtException e) {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token has expired.", e.getMessage());
                return; // Stop further processing
            } catch (SignatureException e) {
                writeErrorResponse(response, HttpStatus.BAD_REQUEST, "JWT token is invalid.", e.getMessage());
                return; // Stop further processing
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT token.",
                        "JWT token validation failed.");
                return; // Stop further processing
            }
        }
        chain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, String details) throws IOException {
        ErrorDetails errorDetails = new ErrorDetails(status, message, details);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(errorDetails.toString());
    }
}

