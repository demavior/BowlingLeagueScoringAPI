package com.acs576.g2.bowlingleaguescoringapi.exception;

import com.acs576.g2.bowlingleaguescoringapi.dto.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The type Custom access denied handler.
 * Author @ParthManaktala
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.FORBIDDEN);
        errorDetails.setMessage("Access Denied to this endpoint");
        errorDetails.setDetails(accessDeniedException.getMessage());

        response.getWriter().println(errorDetails);
    }
}
