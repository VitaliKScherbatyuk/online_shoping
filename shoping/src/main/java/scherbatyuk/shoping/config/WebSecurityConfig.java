/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import scherbatyuk.shoping.security.CustomUserDetailsService;

/**
 * configuration class for configuring web application security in the Spring Framework
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * method defines the passwordEncoder bean used to encode user passwords
     * @return
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * method configures AuthenticationManagerBuilder to use UserDetailsService
     * and passwordEncoder to authenticate users
     * @param auth
     * @throws Exception
     */
    public void configAuthentification(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * method configures access and authorization rules for different URL paths
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/home").authenticated() // Сторінка home доступна тільки для аутентифікованих користувачів
                .antMatchers("/users").hasAuthority("Admin")
                .antMatchers("/buckets").hasAuthority("User")
                .antMatchers("/createProduct").hasAuthority("Admin")
                .antMatchers("/statusAdmin").hasAuthority("Admin")
                .antMatchers("/orderAdministrator").hasAuthority("User")
                .anyRequest().permitAll().and()

                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home").usernameParameter("email").passwordParameter("password").and()
                .logout().logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and()
                .exceptionHandling().accessDeniedPage("/403").and()
                .csrf().disable();
    }
}
