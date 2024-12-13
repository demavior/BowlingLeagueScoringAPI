package com.acs576.g2.bowlingleaguescoringapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * The type Error details.
 * Author @ParthManaktala
 */
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private String details;
    private LocalDateTime localDateTime;

    /**
     * Instantiates a new Error details.
     */
    public ErrorDetails() {
        this.localDateTime = LocalDateTime.now();
    }

    /**
     * Instantiates a new Error details.
     *
     * @param status  the status
     * @param message the message
     * @param details the details
     */
    public ErrorDetails(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.localDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
