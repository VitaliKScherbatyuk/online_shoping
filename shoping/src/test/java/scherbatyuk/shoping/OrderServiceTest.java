package scherbatyuk.shoping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scherbatyuk.shoping.domain.Order;
import scherbatyuk.shoping.service.OrderService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testSaveOrder() {
        // Given
        Order order = new Order();
        // Set order properties

        // When
        orderService.saveOrder(order);

        // Then
        assertNotNull(order.getId());
    }

    @Test
    public void testFindById() {
        // Given
        Order savedOrder = orderService.saveOrder(new Order());

        // When
        Order foundOrder = orderService.findById(savedOrder.getId());

        // Then
        assertNotNull(foundOrder);
        assertEquals(savedOrder.getId(), foundOrder.getId());
    }
}
