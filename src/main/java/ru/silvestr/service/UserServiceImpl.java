package ru.silvestr.service;

import ru.silvestr.repository.RoleRepository;
import ru.silvestr.repository.UserRepository;
import ru.silvestr.models.Role;
import ru.silvestr.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(passwordCoder(user));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleRepository.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleRepository.findById(1L).orElse(null));
        roles2.add(roleRepository.findById(2L).orElse(null));
        User user1 = new User("User","Userov",(byte) 25, "user@mail.com", "user","user",roles1);
        User user2 = new User("Admin","Adminov",(byte) 30, "admin@mail.com", "admin","admin",roles2);
        save(user1);
        save(user2);
        }
}

