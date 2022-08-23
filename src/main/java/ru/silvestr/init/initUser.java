package ru.silvestr.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.silvestr.model.Role;
import ru.silvestr.model.User;
import ru.silvestr.repository.RoleRepository;
import ru.silvestr.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class initUser implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public initUser(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public void run(ApplicationArguments args) {
        roleRepository.save(new Role("ROLE_USER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        User user = new User();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).orElse(null));

        user.setUserId(1L);
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName("Alex");
        user.setSurname("Ipa");
        user.setUsername("user");
        user.setAge((byte) 34);
        user.setRoles(roleSet);
        userRepository.save(user);

        roleSet.clear();
        roleSet.add(roleRepository.findById(2L).orElse(null));
        user.setUserId(2L);
        user.setEmail("admin@mail.ru");
        user.setPassword("admin");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setName("Mike");
        user.setSurname("Key");
        user.setUsername("admin");
        user.setAge((byte) 34);
        user.setRoles(roleSet);
        userRepository.save(user);

        userRepository.save(user);
    }
}