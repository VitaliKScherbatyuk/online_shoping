package scherbatyuk.shoping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.service.CategoriaService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @Test
    public void testGetOrCreateCategoria() {
        // Given
        String categoryTitle = "TestCategory";

        // When
        Categoria categoria = categoriaService.getOrCreateCategoria(categoryTitle);

        // Then
        assertNotNull(categoria);
        assertEquals(categoryTitle, categoria.getCategoryTitle());
    }

    @Test
    public void testFindById() {
        // Given
        String categoryTitle = "TestCategory";
        Categoria savedCategoria = categoriaService.getOrCreateCategoria(categoryTitle);

        // When
        Categoria foundCategoria = categoriaService.findById(savedCategoria.getId());

        // Then
        assertNotNull(foundCategoria);
        assertEquals(savedCategoria.getId(), foundCategoria.getId());
    }

}
