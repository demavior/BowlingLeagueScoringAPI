package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.request.ChangePasswordRequest;
import com.acs576.g2.bowlingleaguescoringapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller for handling user-related requests.
 * This controller provides endpoints for user registration, fetching user details by username, etc.
 *
 * @author Owaiz Mohammed
 */
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user with the provided user details.
     *
     * @param userCredentialDTO the DTO containing user registration information
     * @return ResponseEntity containing the registered UserCredsDTO and the HTTP status
     */
    @PostMapping("/public/register")

    public ResponseEntity<UserCredentialDTO> registerUser(@Valid @RequestBody UserCredentialDTO userCredentialDTO) {
        return new ResponseEntity<>(userService.registerUser(userCredentialDTO), HttpStatus.CREATED);
    }

    @GetMapping("/user/details")
    public ResponseEntity<UserDetailsResponseDTO> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserDetails(authentication.getName()));
    }

    @PatchMapping("/user/details")
    public ResponseEntity<UserDetailsResponseDTO> editUserDetails(@Valid @RequestBody UserDetailsRequestDTO userDetailsRequestDTO, Authentication authentication) {
        return new ResponseEntity<>(userService.editUserDetails(userDetailsRequestDTO, authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<String> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {

        userService.changeUserPassword(authentication.getName(), request.getCurrentPassword(), request.getNewPassword());
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

}
