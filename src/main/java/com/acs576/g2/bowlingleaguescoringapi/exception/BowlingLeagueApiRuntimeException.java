package com.acs576.g2.bowlingleaguescoringapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The type Bowling league api runtime exception.
 * Author @ParthManaktala
 */
@Getter
public class BowlingLeagueApiRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    /**
     * Instantiates a new Bowling league api runtime exception.
     *
     * @param message the message
     */
    public BowlingLeagueApiRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
