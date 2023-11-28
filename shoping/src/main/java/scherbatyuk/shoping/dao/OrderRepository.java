/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import scherbatyuk.shoping.domain.Order;
import scherbatyuk.shoping.domain.User;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Integer> {
    List<Order> findByUser(User user);
}
