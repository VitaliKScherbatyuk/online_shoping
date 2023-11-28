/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import scherbatyuk.shoping.domain.Categoria;
import scherbatyuk.shoping.domain.Product;
import scherbatyuk.shoping.domain.User;
import scherbatyuk.shoping.domain.UserRole;
import scherbatyuk.shoping.security.CustomUserDetails;
import scherbatyuk.shoping.service.CategoriaService;
import scherbatyuk.shoping.service.ProductService;
import scherbatyuk.shoping.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring MVC controller that handles HTTP requests and manages
 * user interaction in your web application
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * method handles HTTP GET requests to the root URL "/" and returns a page named "start"
     *
     * @return
     */
    @GetMapping("/")
    public String startPage(Model model, @RequestParam(name = "lang", required = false) String lang,
                            HttpServletRequest request, HttpServletResponse response) {
        if (lang != null) {
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(lang));
        }

        List<Product> products = productService.getAllProduct();
        List<Categoria> categoriaList = categoriaService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categoriaAll", categoriaList);
        return "start";
    }


    /**
     * method is responsible for processing HTTP GET requests to the URL path "/login".
     * It initializes a User object and returns a "login" page along with a model
     * that contains that object to create a login form
     *
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * method handles HTTP POST requests to the URL path "/login" to authenticate the user.
     * It will try to find the user by email, check the password and redirect to
     * the "home" page if the authentication is successful. If authentication fails,
     * it will display an error message on the "login" page
     *
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute("user") User user, Model model) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid login or password.");
            return "login";
        }
    }

    /**
     * method is responsible for processing HTTP GET requests to the URL path "/registration".
     * It initializes a User object and returns a "registration" page along with a model
     * that contains this object to create a user registration form
     *
     * @param model
     * @return
     */
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * method handles HTTP POST requests to the URL path "/registration" to create a new user.
     * It uses userService to save the new user and redirects the user to the "login"
     * page after successful registration
     *
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/registration")
    public String registrationSubmit(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getRepeatingThePassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "registration";
        }
        userService.save(user);
        return "redirect:/login";
    }

    /**
     * method is responsible for processing HTTP GET requests to the URL path "/home"
     * and returns the page "home"
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userService.findByEmail(userEmail);
        UserRole role = user.getRole();

        model.addAttribute("user", user);
        model.addAttribute("role", role);
        model.addAttribute("products", productService.getAllProduct());
        return "home";
    }

    /**
     * method handles HTTP GET requests to the URL path "/logout". It calls the
     * request.logout() method to log the user out and redirects the user to the "login"
     * page after logging out
     * @param request
     * @return
     * @throws ServletException
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    /**
     * Gets a list of all users using the getAllUser method of the UserService service.
     * Adds the resulting list of users to the model for use on the page.
     * Returns the name of the "users" page.
     * @param model
     * @return
     */
    @RequestMapping("/users")
    public String userPage(Model model){
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Gets the user ID from the path and retrieves the user object using the findById method of the UserService service.
     * Checks if the user exists.
     * If the user exists, adds it to the model for use on the edit user page ("editUser").
     * If the user is not found, redirects to a page with a list of users ("/users").
     * Returns the name of the page to be displayed.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/userPage/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "editUser";
        } else {
            return "redirect:/users";
        }
    }
}
