/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.BucketRepository;
import scherbatyuk.shoping.domain.Bucket;
import scherbatyuk.shoping.domain.Order;
import scherbatyuk.shoping.domain.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * service class that provides methods for interacting with Bucket entities in the database.
 */
@Service
public class BucketService {

    private final BucketRepository bucketRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    /**
     * Returns a list of all Bucket objects that are in the database.
     * @return
     */
    public List<Bucket> getAll() {
        return bucketRepository.findAll();
    }

    /**
     * Stores a Bucket object in the database.
     * @param bucket
     */
    public void add(Bucket bucket) {
        bucketRepository.save(bucket);
    }

    /**
     * Deletes a Bucket object by its ID.
     * @param id
     */
    public void deleteById(Integer id) {
        bucketRepository.deleteById(id);
    }

    /**
     * Finds and returns a Bucket object by its ID. If not found, returns null.
     * @param id
     * @return
     */
    public Bucket findById(Integer id) {
        return bucketRepository.findById(id).orElse(null);
    }

    /**
     * Returns a list of Bucket objects associated with a specific user.
     * @param user
     * @return
     */
    public List<Bucket> getBucketsByUser(User user) {
        List<Bucket> userBuckets = bucketRepository.findByUser(user);
        return userBuckets;
    }

    /**
     * Updates the data for each Bucket object in the passed list.
     * @param bucketItems
     */
    public void update(List<Bucket> bucketItems) {
        for (Bucket bucketItem : bucketItems) {
            bucketRepository.save(bucketItem);
        }
    }

    /**
     * Updates data for a specific Bucket object.
     * @param bucket
     */
    public void updateAmount(Bucket bucket) {
        bucketRepository.save(bucket);
    }

    /**
     * Returns a list of Bucket objects that have not yet been committed.
     * @param userBuckets
     * @return
     */
    public List<Bucket> getUnconfirmedBuckets(List<Bucket> userBuckets) {
        return userBuckets.stream()
                .filter(bucket -> !bucket.isCheck())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of Bucket objects associated with a specific order.
     * @param order
     * @return
     */
    public List<Bucket> getBucketsByOrder(Order order) {
        return order.getBuckets();
    }

}


