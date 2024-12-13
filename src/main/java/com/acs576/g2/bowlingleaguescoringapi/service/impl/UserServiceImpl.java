package com.acs576.g2.bowlingleaguescoringapi.service.impl;

import com.acs576.g2.bowlingleaguescoringapi.constants.RoleName;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Role;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;
import com.acs576.g2.bowlingleaguescoringapi.repository.RoleRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserCredsRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserDetailsRepository;
import com.acs576.g2.bowlingleaguescoringapi.service.UserService;
import com.acs576.g2.bowlingleaguescoringapi.utils.UserCredsDtoEntityConverter;
import com.acs576.g2.bowlingleaguescoringapi.utils.UserDetailsDtoEntityConverter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service implementation for managing user-related operations.
 * This service handles the business logic for user operations such as registration and retrieval.
 *
 * @author Owaiz Mohammed
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserCredsRepository userCredsRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserCredsRepository userCredsRepository, UserDetailsRepository userDetailsRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userCredsRepository = userCredsRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with the provided user details.
     * The method handles user registration including encoding the password and assigning roles.
     *
     * @param userCredentialDto the DTO containing user registration information
     * @return the registered user in the form of a UserDto
     * @throws IllegalArgumentException if the username already exists
     */
    public UserCredentialDTO registerUser(UserCredentialDTO userCredentialDto) {
        if (userCredsRepository.findByEmail(userCredentialDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email " + userCredentialDto.getEmail() + " already exists");
        }

        UserCredentials userCredentials = UserCredsDtoEntityConverter.userCredsDtoToUser(userCredentialDto);
        userCredentials.setPassword(passwordEncoder.encode(userCredentialDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        switch (userCredentialDto.getRole().toUpperCase()) {
            case "ADMIN":
                roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN.name())
                                        .orElseThrow(() -> new IllegalArgumentException("Role ADMIN not found")));
                // Admins will also have user role, break omitted purposely.
            case "USER":
                roles.add(roleRepository.findByName(RoleName.ROLE_USER.name())
                                        .orElseThrow(() -> new IllegalArgumentException("Role USER not found")));
                break;
            default:
                throw new IllegalArgumentException("Invalid User Role");
        }

        userCredentials.setRoles(roles);

        UserDetails userDetails = new UserDetails();
        userDetails.setUserCredentials(userCredentials); // Set the back reference
        userCredentials.setUserDetails(userDetails); // Associate UserDetails with UserCredentials


        return UserCredsDtoEntityConverter.userCredsToUserCredsDto(userCredsRepository.save(userCredentials));
    }


    @Override
    public UserDetailsResponseDTO editUserDetails(UserDetailsRequestDTO userDetailsDTO, String email) {

        // Retrieving UserDetails by user credentials
        Optional<UserDetails> existingUserDetailsOptional = userDetailsRepository.findUserDetailsByEmail(email);


        AtomicReference<UserDetails> savedUserDetails = new AtomicReference<>();
        // edit existing details
        existingUserDetailsOptional.ifPresentOrElse(userDetails -> {
            userDetails.setFirstName(userDetailsDTO.getFirstName());
            userDetails.setLastName(userDetailsDTO.getLastName());
            userDetails.setMiddleName(userDetailsDTO.getMiddleName());

            savedUserDetails.set(userDetailsRepository.save(userDetails));
        }, () -> {
            //Create new user details
            Optional<UserCredentials> userCredentialsOptional = userCredsRepository.findByEmail(email);
            UserCredentials userCredentials = userCredentialsOptional.orElseThrow();

            UserDetails userDetails = new UserDetails();
            userDetails.setFirstName(userDetailsDTO.getFirstName());
            userDetails.setLastName(userDetailsDTO.getLastName());
            userDetails.setMiddleName(userDetailsDTO.getMiddleName());

            userDetails.setUserCredentials(userCredentials);

            savedUserDetails.set(userDetailsRepository.save(userDetails));
        });

        // Convert and return the updated user details DTO
        return UserDetailsDtoEntityConverter.userDetailsToUserDetailsResponseDto(savedUserDetails.get());
    }


    @Override
    public void changeUserPassword(String email, String currentPassword, String newPassword) {
        // Find the user credentials by email
        UserCredentials userCredentials = userCredsRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email));

        // Checks if the provided current password matches the stored password
        if (!passwordEncoder.matches(currentPassword, userCredentials.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password");
        }

        // Checks if the new password is different from the current password
        if (passwordEncoder.matches(newPassword, userCredentials.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the current password");
        }

        // Update the password with the new hashed password
        userCredentials.setPassword(passwordEncoder.encode(newPassword));
        userCredsRepository.save(userCredentials);
    }

    @Override
    public UserDetailsResponseDTO getUserDetails(String email) {
        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(email).orElseGet(() -> {

            /*
             * Since this method is called only by authenticated users,
             * return empty details with just email id if the user details are
             * not found and do not throw error.
             */

            UserDetails emptyUserDetails = new UserDetails();
            UserCredentials emptyUserCredentials = new UserCredentials();

            emptyUserCredentials.setEmail(email);
            emptyUserDetails.setUserCredentials(emptyUserCredentials);

            return emptyUserDetails;
        });
        return UserDetailsDtoEntityConverter.userDetailsToUserDetailsResponseDto(userDetails);
    }

}