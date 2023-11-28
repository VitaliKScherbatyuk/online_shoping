/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.service;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Data
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    private String productName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Price is required")
    private Double price;
    @NotBlank(message = "ProductCode is required")
    private Integer productCode;
    @NotBlank(message = "Remainder is required")
    private Integer remainder;
    @NotBlank(message = "Image is required")
    private MultipartFile image;
    @NotBlank(message = "Categories is required")
    private List<Categoria> categories;

    public static Product createEntity(byte[] imageBytes, String productName, String description,
                                       Double price, Integer productCode, Integer remainder,
                                       List<Categoria> categories) throws IOException {
        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setProductCode(productCode);
        product.setRemainder(remainder);
        product.setEncodedImage(Base64.getEncoder().encodeToString(imageBytes));
        product.setCategory(categories);

        return product;
    }

    public void setEncodedImage(String encodedImage) {
    }
}

