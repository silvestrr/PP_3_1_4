package ru.silvestr.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.silvestr.repository.RoleRepository;
import ru.silvestr.repository.UserRepository;
import ru.silvestr.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }
    @Transactional
    @Override
    public void save(User user) {
        userRepository.save(passwordCoder(user));
    }
    @Transactional
    @Override
    public void update(long id, User user) {
        User user1 = userRepository.findById(id).get();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setAge(user.getAge());
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
    }
    @Transactional
    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(s);
        } catch (UsernameNotFoundException u) {
            throw new UsernameNotFoundException("user not found");
        }
    }


}

