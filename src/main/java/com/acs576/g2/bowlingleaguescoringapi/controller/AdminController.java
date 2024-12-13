package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller for handling user-related requests.
 * This controller provides endpoints for user registration, fetching user details by username, etc.
 *
 * @author Owaiz Mohammed
 */
@RestController
public class AdminController {

    private final AdminService adminService;

    /**
     * Instantiates a new User controller.
     *
     * @param adminService the user service
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    /**
     * Gets user by email.
     *
     * @return the user by email
     */
    @GetMapping("/admin/users/all")
    public ResponseEntity<List<UserDetailsResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Retrieves a user details by their email.
     *
     * @param email the email of the user to be retrieved
     * @return ResponseEntity containing the found UserDetailsDto and the HTTP status
     */
    @GetMapping("/admin/user-details/{email}")
    public ResponseEntity<UserDetailsResponseDTO> getUserDetailsByEmail(@PathVariable String email) {
        return new ResponseEntity<>(adminService.getUserDetailsByEmail(email), HttpStatus.OK);
    }

}

