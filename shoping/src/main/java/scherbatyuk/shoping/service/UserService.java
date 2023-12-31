/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.UserRepository;
import scherbatyuk.shoping.domain.User;
import scherbatyuk.shoping.domain.UserRole;

import java.util.List;

/**
 * service class that provides methods for interacting with the User entity in the database.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Takes a User object and stores it in the database. The user's password is hashed
     * using PasswordEncoder before saving. Also sets the user role to UserRole.User.
     * @param user
     */
    public void save (User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.User);
        userRepository.save(user);
    }

    /**
     * Accepts the user's email and returns the User object found by the specified email.
     * @param email
     * @return
     */
    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    /**
     * Returns a list of all users that are in the database.
     * @return
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Takes a user identifier (id) and returns a User object if it exists in the database.
     * If not found, returns null.
     * @param id
     * @return
     */
    public User findById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Takes the user ID (id) and removes it from the database.
     * @param id
     */
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }

}
