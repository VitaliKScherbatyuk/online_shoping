/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.UserRepository;
import scherbatyuk.shoping.domain.User;

import java.util.Collections;
import java.util.Optional;

/**
 * implementation of the UserDetailsService interface in Spring Security and is
 * responsible for loading information about the user during authentication
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * designed to load information about a user by their username, which in this case is an email
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            User user =userOptional.get();
            return new CustomUserDetails(user, Collections.singletonList(user.getRole().toString()));
        }
        throw new UsernameNotFoundException("No user present with email:" + email);
    }
}
