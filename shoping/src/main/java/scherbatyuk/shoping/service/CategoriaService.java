/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.CategoriaRepository;
import scherbatyuk.shoping.dao.ProductRepository;
import scherbatyuk.shoping.domain.Categoria;

import java.util.List;

/**
 * service class that provides methods for interacting with Categoria entities in the database.
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Returns a list of all Categoria objects that are in the database.
     * @return
     */
    public List<Categoria> getAllCategories() {
        return categoriaRepository.findAll();
    }

    /**
     * Takes a category title (categoryTitle) and checks if it already exists in the database.
     * If the category does not exist, it creates a new Categoria object, sets its name and stores it in the database.
     * Returns an existing or newly created category.
     * @param categoryTitle
     * @return
     */
    public Categoria getOrCreateCategoria(String categoryTitle) {
        categoryTitle = categoryTitle.replace("\"", "").trim();
        Categoria categoria = categoriaRepository.findByCategoryTitle(categoryTitle);
        if (categoria == null) {
            categoria = new Categoria();
            categoria.setCategoryTitle(categoryTitle);
            categoriaRepository.save(categoria);
        }
        return categoria;
    }

    /**
     * Finds and returns a Categoria object by its ID. If not found, returns null.
     * @param id
     * @return
     */
    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }
}



