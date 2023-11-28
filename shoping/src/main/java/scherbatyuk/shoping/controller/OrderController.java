/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import scherbatyuk.shoping.domain.Bucket;
import scherbatyuk.shoping.domain.Order;
import scherbatyuk.shoping.domain.Status;
import scherbatyuk.shoping.domain.User;
import scherbatyuk.shoping.service.BucketService;
import scherbatyuk.shoping.service.OrderService;
import scherbatyuk.shoping.service.ProductService;
import scherbatyuk.shoping.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * controller responsible for handling HTTP requests related to orders and the shopping bucket
 */
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final BucketService bucketService;
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderController(BucketService bucketService, UserService userService, OrderService orderService, ProductService productService) {
        this.bucketService = bucketService;
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    /**
     * Automatically determines the current user by obtaining information from the security context.
     * Gets all of the user's shopping carts and selects those that are not yet confirmed.
     * Calculates the total amount of all orders and adds it to the model.
     * Returns the view name "order" to display the shopping cart and order confirmation form.
     *
     * @param model
     * @return
     */
    @GetMapping("/order")
    public String viewUnconfirmedBuckets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);

        if (user != null) {
            List<Bucket> userBuckets = bucketService.getBucketsByUser(user);
            List<Bucket> unconfirmedBuckets = bucketService.getUnconfirmedBuckets(userBuckets);

            if (unconfirmedBuckets.isEmpty()) {
                model.addAttribute("emptyBucketMessage", "There are no unconfirmed buckets.");
            } else {
                model.addAttribute("order", unconfirmedBuckets);

                double totalSum = calculateTotalSum(unconfirmedBuckets);
                model.addAttribute("totalSum", totalSum);
            }
            return "order";
        } else {
            logger.error("OrderController: viewUnconfirmedBuckets user == null" + user.getId());
            return "403";
        }
    }

    /**
     * Calculates the total amount of all orders in the basket based on the
     * prices of the products and their quantity.
     * @param buckets
     * @return
     */
    private double calculateTotalSum(List<Bucket> buckets) {
        double totalSum = 0;
        for (Bucket bucket : buckets) {
            totalSum += (bucket.getProduct().getPrice() * bucket.getAmount());
        }
        return totalSum;
    }

    /**
     * Checks for input errors. If so, returns to the order page to correct them.
     * Gets the current user and their shopping cart.
     * Checks product balance before confirming order. If the balance is less than the quantity in the order, displays a message about the shortage of the product and returns to the shopping cart page.
     * Reduces the product balance and saves the order in the database.
     * Sets the status of the order, updates the cart status and displays the corresponding message.
     * Returns to the confirmation page or the bank payment page, depending on the payment method selected.
     * @param order
     * @param bindingResult
     * @param address
     * @param phone
     * @param payment
     * @param notes
     * @param recipient
     * @param model
     * @return
     */
    @PostMapping("/saveOrder")
    public String saveOrder(@Valid @ModelAttribute Order order, BindingResult bindingResult, String address, String phone,
                            String payment, String notes, String recipient, Model model) {

        if (bindingResult.hasErrors()) {
            return "order";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);

        if (user != null) {
            List<Bucket> userBuckets = bucketService.getBucketsByUser(user);
            List<Bucket> unconfirmedBuckets = bucketService.getUnconfirmedBuckets(userBuckets);

            for (Bucket bucket : unconfirmedBuckets) {
                int remainder = bucket.getProduct().getRemainder();
                int amount = bucket.getAmount();

                if (remainder < amount) {
                    model.addAttribute("insufficientStockMessage", "Insufficient stock for product: " + bucket.getProduct().getProductName());
                    return "bucket";
                }

                remainder -= amount;
                bucket.getProduct().setRemainder(remainder);
                productService.updateProduct(bucket.getProduct());
            }

            double totalSum = calculateTotalSum(unconfirmedBuckets);
            order.setTotalSum(totalSum);
            order.setAddress(address);
            order.setUser(user);
            order.setRecipient(recipient);
            order.setPhone(phone);
            order.setPayment(payment);
            order.setNotes(notes);
            order.setCreateOrder(LocalDateTime.now());
            order.setStatus(Status.New);
            orderService.saveOrder(order);

            for (Bucket bucket : unconfirmedBuckets) {
                bucket.setOrder(orderService.findById(order.getId()));
                bucket.setCheck(true);
            }

            bucketService.update(unconfirmedBuckets);
            if (order.getPayment().equals("Bank")) {
                return "bankPay";
            } else {
                return "confirmation";
            }

        } else {
            logger.error("OrderController: saveOrder user == null" + user.getId());
            return "403";
        }
    }


    @GetMapping("/order-form")
    public String showOrderForm() {
        return "order-form";
    }

    @GetMapping("/confirmation")
    public String showConfirmation() {
        return "confirmation";
    }

    @GetMapping("/bankPay")
    public String bankPay() {
        return "bankPay";
    }

}

