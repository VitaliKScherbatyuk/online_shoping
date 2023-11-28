/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import scherbatyuk.shoping.domain.User;

import java.util.Optional;


/**
 * create an interface for searching for a user in the database by the value of email,
 * and extend the functionality with the help of JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

}
