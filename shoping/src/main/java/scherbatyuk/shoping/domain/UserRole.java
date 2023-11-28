/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * use authentication based on the admin and user values
 */
public enum UserRole implements GrantedAuthority {
    Admin, User;

    @Override
    public String getAuthority() {
        return name();
    }
}
