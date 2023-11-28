/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import scherbatyuk.shoping.domain.*;
import scherbatyuk.shoping.service.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The BucketController is responsible for handling requests related to the shopping basket.
 */
@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public BucketController(BucketService bucketService, UserService userService, ProductService productService) {
        this.bucketService = bucketService;
        this.userService = userService;
        this.productService = productService;
    }

    /**
     * Gets information about the authenticated user.
     * Gets buckets that have not been checked (!bucket.isCheck()) for this user.
     * Checks if the recycle bin is empty and sets the corresponding message to the model.
     * Returns the corresponding view ("bucket").
     * @param model
     * @return
     */
    @GetMapping
    public String bucketPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);

        if (user != null) {
            List<Bucket> buckets = bucketService.getBucketsByUser(user);

            List<Bucket> nonCheckedBuckets = buckets.stream()
                    .filter(bucket -> !bucket.isCheck())
                    .collect(Collectors.toList());

            if (nonCheckedBuckets.isEmpty()) {
                model.addAttribute("emptyBucketMessage", "Your bucket is empty.");
            } else {
                model.addAttribute("buckets", nonCheckedBuckets);
            }

            return "bucket";
        } else {
            return "redirect:/home";
        }
    }

    /**
     * Gets the product by its ID.
     * Creates a new Bucket object and fills it with data.
     * Adds this Bucket object to the database using bucketService.add(bucket).
     * Redirects the user to the shopping cart page ("/bucket").
     * @param productId
     * @return
     */
    @PostMapping
    public String createBucket(@RequestParam String productId) {
        Product product = productService.findById(Integer.parseInt(productId));

        Bucket bucket = new Bucket();
        bucket.setProduct(product);
        bucket.setPurchaseDate(new Date());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);
        bucket.setUser(user);
        if (bucket.getAmount() == null) {
            bucket.setAmount(1);
        }

        bucketService.add(bucket);
        return "redirect:/bucket";
    }

    /**
     * Deletes a Bucket object from the database by its identifier (id).
     * Redirects the user to the shopping cart page ("/bucket").
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deletedBucketId(@PathVariable Integer id, Model model) {
        bucketService.deleteById(id);
        return "redirect:/bucket";
    }

    /**
     * Gets a Bucket object by its identifier (id).
     * Checks whether the entered amount is less than or equal to the product balance.
     * If so, sets the amount for the Bucket object, otherwise sets it equal to the product balance.
     * Updates the Bucket object in the database using bucketService.updateAmount(bucket).
     * Redirects the user to the shopping cart page ("/bucket").
     * @param id
     * @param amount
     * @param model
     * @return
     */
    @PostMapping("/updateAmount/{id}")
    public String updateAmountBucketId(@PathVariable Integer id, @RequestParam Integer amount, Model model) {
        Bucket bucket = bucketService.findById(id);

        if (bucket != null) {
            int productRemainder = bucket.getProduct().getRemainder();

            if (amount <= productRemainder) {
                bucket.setAmount(amount);
            } else {
                bucket.setAmount(productRemainder);
                model.addAttribute("bucket", bucket);
            }
        }

        bucketService.updateAmount(bucket);
        return "redirect:/bucket";
    }

    /**
     * When the product quantity is updated, it displays the products in the shopping cart.
     * Works like the bucketPage method
     * @param model
     * @return
     */
    @PostMapping("/updateAll")
    public String updateAllBuckets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);

        if (user != null) {
            List<Bucket> buckets = bucketService.getBucketsByUser(user);

            List<Bucket> nonCheckedBuckets = buckets.stream()
                    .filter(bucket -> !bucket.isCheck())
                    .collect(Collectors.toList());

            if (nonCheckedBuckets.isEmpty()) {
                model.addAttribute("emptyBucketMessage", "Your bucket is empty.");
            } else {
                model.addAttribute("buckets", nonCheckedBuckets);
            }
            return "redirect:/bucket";
        } else {
            return "redirect:/home";
        }
    }

}