package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.interfaces.IRoleRepository;
import com.alexbych.bookstore.backend.interfaces.IUserRepository;
import com.alexbych.bookstore.model.Role;
import com.alexbych.bookstore.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User update(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            Role role = new Role();
            role.setId(1);
            user.setRole(role);
            userRepository.update(user);
            return user;
        } else return null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        user.getAuthorities();
        return user;
    }
}