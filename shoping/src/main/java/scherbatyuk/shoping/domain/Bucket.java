/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "buckets")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Column(name = "purchase_date")
    private Date purchaseDate;
    private Integer amount;
    @ManyToOne
    private Order order;
    @Column(name = "check_field")
    private boolean check;

    @Override
    public String toString() {
        return "Bucket{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", product=" + (product != null ? product.getId() : null) +
                ", purchaseDate=" + purchaseDate +
                ", amount=" + amount +
                ", order=" + (order != null ? order.getId() : null) +
                ", check=" + check +
                '}';
    }
}

