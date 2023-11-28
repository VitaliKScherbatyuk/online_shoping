package scherbatyuk.shoping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scherbatyuk.shoping.domain.Product;
import scherbatyuk.shoping.service.ProductService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testSaveProduct() {
        // Given
        Product product = new Product();

        // When
        productService.saveProduct(product);

        // Then
        assertNotNull(product.getId());
    }

    @Test
    public void testFindById() {
        // Given
        Product savedProduct = productService.saveProduct(new Product());

        // When
        Product foundProduct = productService.findById(savedProduct.getId());

        // Then
        assertNotNull(foundProduct);
        assertEquals(savedProduct.getId(), foundProduct.getId());
    }

}
