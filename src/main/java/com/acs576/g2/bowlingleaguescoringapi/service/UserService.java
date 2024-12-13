package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;

import java.util.List;

public interface UserService {

    UserCredentialDTO registerUser(UserCredentialDTO userCredentialDto);

    UserDetailsResponseDTO editUserDetails(UserDetailsRequestDTO userDetailsDTO, String email);

    void changeUserPassword(String email, String currentPassword, String newPassword);

    UserDetailsResponseDTO getUserDetails(String email);
  
}
