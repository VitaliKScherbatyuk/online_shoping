/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;
import scherbatyuk.shoping.service.CategoriaService;
import scherbatyuk.shoping.service.ProductDTO;
import scherbatyuk.shoping.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * controller responsible for handling HTTP requests related to products
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Checks for input errors. If so, returns the add product page with relevant errors.
     * Gets the product data from the ProductDTO object, including the image (if one exists).
     * Splits the category string into separate category headers and gets or creates Categoria objects for each category.
     * Creates a Product object from the received data and stores it in the database via the ProductService.
     * Returns a redirect to the home page after successful product creation.
     * @param productDTO
     * @param bindingResult
     * @return
     * @throws IOException
     */
    @PostMapping("/createProduct")
    public ModelAndView createProduct(@Valid  @ModelAttribute("product") ProductDTO productDTO,
                                      BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("createProduct");
        }

        MultipartFile image = productDTO.getImage();
        String categoryInput = productDTO.getCategories().toString();
        String[] categoryTitles = categoryInput.split(",");

        List<Categoria> categories = new ArrayList<>();
        for (String categoryTitle : categoryTitles) {
            Categoria categoria = categoriaService.getOrCreateCategoria(categoryTitle.trim());
            categories.add(categoria);
        }

        Product product = ProductDTO.createEntity(image.getBytes(), productDTO.getProductName(),
                productDTO.getDescription(), productDTO.getPrice(),
                productDTO.getProductCode(), productDTO.getRemainder(), categories);

        productService.saveProduct(product);
        return new ModelAndView("redirect:/home");
    }

    /**
     * Initializes a new ProductDTO object and passes it to the page to add the new product.
     * @param model
     * @return
     */
    @GetMapping("/createProduct")
    public String addProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "createProduct";
    }

    /**
     * Gets the product by its ID and deletes it via ProductService.
     * Returns a redirect to the home page after successfully uninstalling the product.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        productService.deleteById(product.getId());
        return "redirect:/home";
    }

    /**
     * Gets data about a product by its ID through the ProductService.
     * Passes product data to the "productDetails" page to display product details.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }

}



