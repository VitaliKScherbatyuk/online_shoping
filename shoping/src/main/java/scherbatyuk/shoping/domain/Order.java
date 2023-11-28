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
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Додайте поле для зв'язку з користувачем
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "Phone is required")
    @Length(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phone;
    private String payment;
    private LocalDateTime createOrder;
    private Double totalSum;
    private String notes;
    private String recipient;

    @OneToMany(mappedBy = "order")
    private List<Bucket> buckets;
    private Status status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", payment='" + payment + '\'' +
                ", createOrder=" + createOrder +
                ", totalSum=" + totalSum +
                ", notes='" + notes + '\'' +
                ", recipient='" + recipient + '\'' +
                ", status=" + status +
                '}';
    }
}

