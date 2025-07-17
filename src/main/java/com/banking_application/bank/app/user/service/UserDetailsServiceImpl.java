package com.banking_application.bank.app.user.service;


import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        // 3. Convert your custom Role objects to Spring Security's GrantedAuthority
        // Assuming user.getRoles() returns a Collection<Role> and Role has a .name() method (for enums)
        // If Role is an entity with a 'name' field, use .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) // Assuming Role is an enum with .name()
                .collect(Collectors.toList());

        // 4. Build and return Spring Security's UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getName(),      // Username
                user.getPassword(),  // Hashed password from the database
                authorities          // Collection of GrantedAuthority objects (e.g., ROLE_ADMIN)
        ) {
            // Add a custom method to get the user ID
            public Long getUserId() {
                return user.getId();
            }
        };

    }

}
