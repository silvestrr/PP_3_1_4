package ru.kata.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.demo.model.Role;
import ru.kata.demo.model.User;
import ru.kata.demo.repository.UserRepository;


import java.util.HashSet;
import java.util.Set;


@Component
public class initUser implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public initUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        User user = new User();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1L, "USER"));

        user.setId(1L);
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName("Alex");
        user.setSurname("Ipa");
        user.setAge(34);
        user.setRoles(roleSet);
        userRepository.save(user);

        roleSet.clear();
        roleSet.add(new Role(2L, "ADMIN"));

        user.setId(2L);
        user.setEmail("admin@mail.ru");
        user.setPassword("admin");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName("Mike");
        user.setSurname("Key");
        user.setAge(34);
        user.setRoles(roleSet);
        userRepository.save(user);

        userRepository.save(user);
    }
}

