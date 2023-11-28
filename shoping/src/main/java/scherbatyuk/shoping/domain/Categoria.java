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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Categoria{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String categoryTitle;

    @Override
    public String toString() {
        return categoryTitle;
    }

}
