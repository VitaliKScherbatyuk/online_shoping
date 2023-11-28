/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, CrudRepository<Product, Integer> {

    List<Product> findByCategory(String categoria);

    List<Product> findAllById(Integer id);
    List<Product> findProductsByCategory(Categoria category);
}
