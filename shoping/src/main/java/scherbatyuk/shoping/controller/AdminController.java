/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scherbatyuk.shoping.domain.Bucket;
import scherbatyuk.shoping.domain.Order;
import scherbatyuk.shoping.domain.Status;
import scherbatyuk.shoping.service.BucketService;
import scherbatyuk.shoping.service.OrderService;

import java.util.List;

/**
 * The AdminController is responsible for handling requests related to administrative functions,
 * such as viewing and changing order statuses.
 */
@Controller
public class AdminController {
    private final BucketService bucketService;
    private final OrderService orderService;

    public AdminController(BucketService bucketService, OrderService orderService) {
        this.bucketService = bucketService;
        this.orderService = orderService;
    }

    /**
     * Gets all records in buckets using bucketService.getAll().
     * Checks if the recycle bin is empty and sets the corresponding message to the model.
     * Returns the corresponding view ("orderAdministrator").
     * @param model
     * @return
     */
    @GetMapping("orderAdministrator")
    public String bucketPage(Model model) {

        List<Bucket> buckets = bucketService.getAll();                  ;

        if (buckets.isEmpty()) {
            model.addAttribute("emptyBucketMessage", "Your bucket is empty.");
        } else {
            model.addAttribute("buckets", buckets);
        }

        return "orderAdministrator";
    }

    /**
     * Gets all records in buckets using bucketService.getAll().
     * Checks if the recycle bin is empty and sets the corresponding message to the model.
     * Returns the corresponding view ("statusAdmin").
     * @param model
     * @return
     */
    @GetMapping("statusAdmin")
    public String statusAdminPage(Model model) {

        List<Bucket> buckets = bucketService.getAll();                  ;

        if (buckets.isEmpty()) {
            model.addAttribute("emptyBucketMessage", "Your bucket is empty.");
        } else {
            model.addAttribute("buckets", buckets);
        }

        return "statusAdmin";
    }

    /**
     * Gets a specific order by its ID (orderId) using orderService.findById(orderId).
     * Checks to see if an order is found and retrieves all cart entries for that order.
     * Adds the required data to the model and returns the view("statusAdminDetails").
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("statusAdminDetails/{orderId}")
    public String statusAdminDetails(@PathVariable Integer orderId, Model model) {

        Order order = orderService.findById(orderId);

        if (order == null) {
            return "redirect:/statusAdmin";
        }

        List<Bucket> buckets = bucketService.getBucketsByOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("buckets", buckets);

        return "statusAdminDetails";
    }

    /**
     * Gets a specific order by its ID (orderId) using orderService.findById(orderId).
     * Sets the new status for the order based on the passed status parameter.
     * Saves the modified order using orderService.saveOrder(order).
     * Redirects the user to the order details page ("/statusAdminDetails/{orderId}").
     * @param orderId
     * @param status
     * @return
     */
    @PostMapping("/changeStatus/{orderId}")
    public String changeStatus(@PathVariable Integer orderId, @RequestParam String status) {

        Order order = orderService.findById(orderId);

        if (order == null) {
            return "redirect:/statusAdmin";
        }

        order.setStatus(Status.valueOf(status));
        orderService.saveOrder(order);

        return "redirect:/statusAdminDetails/" + orderId;
    }
}