/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;
import scherbatyuk.shoping.service.BucketService;
import scherbatyuk.shoping.service.CategoriaService;
import scherbatyuk.shoping.service.ProductService;

import java.util.List;

/**
 * controller that handles HTTP requests related to product categories.
 */
@Controller
@RequestMapping
public class CategoriaController {

    private final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProductService productService;

    /**
     * Gets a category by its identifier (id) using categoriaService.findById(id).
     * If the category exists, gets the list of products associated with that category using productService.findProductsByCategoria(category).
     * Adds the resulting category and product list to the model.
     * Returns the view name "categoryDetails".
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/category/{id}")
    public String viewCategoryDetails(@PathVariable Integer id, Model model) {

        Categoria category = categoriaService.findById(id);
        if (category != null) {
            List<Product> productList = productService.findProductsByCategoria(category);
            model.addAttribute("category", category);
            model.addAttribute("products", productList);
            return "categoryDetails";
        } else {
            logger.error("CategoriaController: viewCategoryDetails category == null" + id);
            return "403";
        }
    }
}