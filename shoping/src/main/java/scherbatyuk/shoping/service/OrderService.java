/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.OrderRepository;
import scherbatyuk.shoping.domain.Order;

/**
 * service class that provides methods for interacting with the Order entity in the database.
 */
@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Takes an order identifier (id) and returns an Order object if it exists in the database.
     * If not found, returns null.
     * @param id
     * @return
     */
    public Order findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Takes an Order object and stores it in the database using a repository.
     *
     * @param order
     * @return
     */
    public Order saveOrder(Order order) {
        orderRepository.save(order);
        return order;
    }
}