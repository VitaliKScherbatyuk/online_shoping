package scherbatyuk.shoping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scherbatyuk.shoping.domain.User;
import scherbatyuk.shoping.domain.UserRole;
import scherbatyuk.shoping.service.UserService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() {
        // Given
        User user = new User();

        // When
        userService.save(user);

        // Then
        assertNotNull(user.getId());
        assertEquals(UserRole.User, user.getRole());
    }

    @Test
    public void testFindByEmail() {
        // Given
        String userEmail = "test@example.com";
        User savedUser = new User();
        savedUser.setEmail(userEmail);
        savedUser.setPassword("testpassword");
        userService.save(savedUser);

        // When
        User foundUser = userService.findByEmail(userEmail);

        // Then
        assertNotNull(foundUser);
        assertEquals(userEmail, foundUser.getEmail());
    }

}
