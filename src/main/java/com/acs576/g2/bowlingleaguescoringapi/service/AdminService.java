package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;

import java.util.List;

public interface AdminService {
    UserDetailsResponseDTO getUserDetailsByEmail(String email);

    List<UserDetailsResponseDTO> getAllUsers();
}
