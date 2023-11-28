/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scherbatyuk.shoping.dao.ProductRepository;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;

import java.util.List;

/**
 * service class that provides methods for interacting with the Product entity in the database.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Takes a Product object and stores it in the database using a repository.
     * Returns the stored Product object.
     * @param product
     * @return
     */
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    /**
     * Returns a list of all products that are in the database.
     * @return
     */
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    /**
     * Takes a product identifier (id) and returns a Product object if it exists in the database.
     * If not found, returns null.
     * @param id
     * @return
     */
    public Product findById(int id){
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Takes the product identifier (id) and removes it from the database.
     * @param id
     */
    public void deleteById(Integer id){
        productRepository.deleteById(id);
    }

    /**
     * Takes a Categoria object and returns a list of products that belong to the specified category.
     * @param category
     * @return
     */
    public List<Product> findProductsByCategoria(Categoria category) {
        return productRepository.findProductsByCategory(category);
    }

    /**
     * Takes a Product object and updates it in the database using the repository.
     * @param product
     */
    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}


