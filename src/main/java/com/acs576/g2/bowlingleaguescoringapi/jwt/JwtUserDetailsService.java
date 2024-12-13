package com.acs576.g2.bowlingleaguescoringapi.jwt;

import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;
import com.acs576.g2.bowlingleaguescoringapi.repository.UserCredsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type Jwt user details service.
 * Author @ParthManaktala
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserCredsRepository userCredsRepository;

    /**
     * Instantiates a new Jwt user details service.
     *
     * @param userCredsRepository the user repository
     */
    public JwtUserDetailsService(UserCredsRepository userCredsRepository) {
        this.userCredsRepository = userCredsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredentials userCredentials = userCredsRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        return new User(userCredentials.getEmail(), userCredentials.getPassword(),
                userCredentials.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }
}
