package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.*;
import com.acs576.g2.bowlingleaguescoringapi.exception.BowlingLeagueApiRuntimeException;
import com.acs576.g2.bowlingleaguescoringapi.repository.*;
import com.acs576.g2.bowlingleaguescoringapi.service.AdminService;
import com.acs576.g2.bowlingleaguescoringapi.utils.UserCredsDtoEntityConverter;
import com.acs576.g2.bowlingleaguescoringapi.utils.UserDetailsDtoEntityConverter;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user-related operations.
 * This service handles the business logic for user operations such as registration and retrieval.
 *
 * @author Owaiz Mohammed
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final UserCredsRepository userCredsRepository;
    private final UserDetailsRepository userDetailsRepository;

    /**
     * Instantiates a new Admin service.
     *
     * @param userCredsRepository   the user creds repository
     * @param userDetailsRepository the user details repository
     */
    public AdminServiceImpl(UserCredsRepository userCredsRepository, UserDetailsRepository userDetailsRepository) {
        this.userCredsRepository = userCredsRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to be retrieved
     * @return the found user as UserDto
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetailsResponseDTO getUserDetailsByEmail(String email) {

        Optional<UserCredentials> userCredentialsOptional = userCredsRepository.findByEmail(email);
        UserCredentials userCredentials = userCredentialsOptional.orElseThrow(() -> new BowlingLeagueApiRuntimeException("User with email not found", HttpStatus.BAD_REQUEST));

        Optional<UserDetails> userDetailsOptional = userDetailsRepository.findUserDetailsByUserCredentials(userCredentials);
        UserDetails userDetails = userDetailsOptional.orElseThrow(() -> new BowlingLeagueApiRuntimeException("User details not found", HttpStatus.BAD_REQUEST));

        return UserDetailsDtoEntityConverter.userDetailsToUserDetailsResponseDto(userDetails);
    }

    @Override
    public List<UserDetailsResponseDTO> getAllUsers() {
        List<UserDetailsResponseDTO> userDetailsResponseDTOList = new ArrayList<>();

        List<UserCredentials> userCredentialsList = userCredsRepository.findAll();

        userCredentialsList.forEach(userCredentials -> {
            UserDetails userDetails = userCredentials.getUserDetails();

            if (userDetails == null) {
                userDetails = new UserDetails();
                userDetails.setUserCredentials(userCredentials);
            }

            UserDetailsResponseDTO userDetailsResponseDTO = UserDetailsDtoEntityConverter.userDetailsToUserDetailsResponseDto(userDetails);
            userDetailsResponseDTOList.add(userDetailsResponseDTO);
        });

        return userDetailsResponseDTOList;
    }

}