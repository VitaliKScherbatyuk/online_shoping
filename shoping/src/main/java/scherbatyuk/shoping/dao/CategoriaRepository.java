/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scherbatyuk.shoping.domain.Categoria;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria findByCategoryTitle(String categoryTitle);

}

