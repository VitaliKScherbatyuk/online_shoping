/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import scherbatyuk.shoping.domain.Bucket;
import scherbatyuk.shoping.domain.Status;
import scherbatyuk.shoping.domain.User;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {
    List<Bucket> findByUser(User user);

    List<Bucket> findByUserAndCheckFalse(User user);

}
