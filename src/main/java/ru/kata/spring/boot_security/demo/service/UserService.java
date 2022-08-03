package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> index();

    User showUserById(Long id);

    User save(User user);

    void saveRole(Role role);

    void update(User user);

    void delete(Long id);

    User findByUsername(String username);
}
