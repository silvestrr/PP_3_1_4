package ru.kata.demo.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.demo.exseption.NoUserByIdException;

import ru.kata.demo.exseption.UserIncorrectId;
import ru.kata.demo.model.Role;
import ru.kata.demo.model.User;
import ru.kata.demo.service.UserService;


import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUserForUser() {
        return ResponseEntity.ok((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }

    @PostMapping("/admin/create")
    public ResponseEntity<UserIncorrectId> createUser(@ModelAttribute User user, Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectId(error), HttpStatus.BAD_REQUEST);
        }
        try {
            role.getRole();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoUserByIdException u) {
            throw new NoUserByIdException("User with username exist");
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<UserIncorrectId> updateUser(@PathVariable("id") long id,
                                                      @Valid @RequestBody User user,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectId(error), HttpStatus.BAD_REQUEST);
        }
        try {
            String oldPassword = userService.getUser(id).getPassword();
            if (oldPassword.equals(user.getPassword())) {
                System.out.println("TRUE");
                user.setPassword(oldPassword);
                userService.updateUser(user);
            } else {
                System.out.println("FALSE");
                userService.addUser(user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoUserByIdException u) {
            throw new NoUserByIdException("User with username exist");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserIncorrectId> deleteUserById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new UserIncorrectId("User deleted"), HttpStatus.OK);
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }

}

