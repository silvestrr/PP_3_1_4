package ru.silvestr.service;

import ru.silvestr.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getById(long id);

    void save(User user);

    void deleteById(long id);

    User findByUsername(String username);

    void update(long id, User user);

    User passwordCoder(User user);
}
