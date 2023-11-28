package scherbatyuk.shoping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scherbatyuk.shoping.service.CategoriaService;
import scherbatyuk.shoping.service.OrderService;
import scherbatyuk.shoping.service.ProductService;
import scherbatyuk.shoping.service.UserService;

@SpringBootTest
class ShopingApplicationTests {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Test
	public void categoriaServiceTests() {
	}

	@Test
	public void orderServiceTests() {
	}

	@Test
	public void productServiceTests() {
	}

	@Test
	public void userServiceTests() {
	}

	@Test
	void contextLoads() {
	}

}
