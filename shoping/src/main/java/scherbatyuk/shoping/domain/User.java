/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * creating a user to work with the database. We use lombok:
 * with arguments in the constructor, without them, exclude "repeatingThePassword"
 * from hashCode and equals and the constructor. We generate setters and getters
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Name is required")
    @Range(min = 3, max = 12, message = "Age must be between 3 and 12")
    private String name;
    @Range(min = 1, max = 120, message = "Age must be between 1 and 120")
    private Integer age;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @Transient
    private String repeatingThePassword;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.age = user.age;
        this.email = user.email;
        this.password = user.password;
        this.role = user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, password, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(age, user.age) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatingThePassword='" + repeatingThePassword + '\'' +
                ", role=" + role +
                '}';
    }
}
