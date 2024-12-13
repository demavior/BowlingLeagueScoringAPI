package com.acs576.g2.bowlingleaguescoringapi.exception;

import com.acs576.g2.bowlingleaguescoringapi.dto.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The type Custom authentication entry point.
 * Author @ParthManaktala
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.FORBIDDEN);
        errorDetails.setMessage("Unauthorized");
        errorDetails.setDetails(authException.getMessage());


        response.getWriter().println(errorDetails);
    }
}