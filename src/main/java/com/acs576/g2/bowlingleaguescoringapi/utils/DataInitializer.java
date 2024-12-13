package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.entity.Role;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;
import com.acs576.g2.bowlingleaguescoringapi.repository.RoleRepository;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserCredsRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * The type Data initializer.
 * Author @ParthManaktala
 */
@Component
@EnableTransactionManagement
public class DataInitializer implements CommandLineRunner {

    private final UserCredsRepository userCredsRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Data initializer.
     *
     * @param userCredsRepository  the user repository
     * @param roleRepository  the role repository
     * @param passwordEncoder the password encoder
     */
    public DataInitializer(UserCredsRepository userCredsRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userCredsRepository = userCredsRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");

        createUserIfNotFound("admin@example.com", "adminpass", new String[]{"ROLE_ADMIN", "ROLE_USER"});
        createUserIfNotFound("user@example.com", "userpass", new String[]{"ROLE_USER"});
    }

    private void createRoleIfNotFound(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            return roleRepository.save(role);
        });
    }

    /**
     * Create user if not found.
     *
     * @param email    the email
     * @param password the password
     * @param roles    the roles
     */
    public void createUserIfNotFound(String email, String password, String[] roles) {
        Optional<UserCredentials> userOptional = userCredsRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            UserCredentials user = new UserCredentials();
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);

            for (String roleName : roles) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.getRoles().add(role);
            }

            userCredsRepository.save(user);
        }

    }
}

