package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitUser {

    private final UserService userService;

    @Autowired
    public InitUser(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void createAdmin() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        userService.saveRole(adminRole);
        userService.saveRole(userRole);

        User admin = new User("admin", "adminov", "admin", "admin@email.ru", "admin");
        admin.addRole(adminRole);

        User user = new User("user", "userov", "user", "user@email.ru", "user");
        user.addRole(userRole);

        userService.save(admin);
        userService.save(user);

    }
}