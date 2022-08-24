package ru.silvestr.controller;

import ru.silvestr.exception.UserIncorrectId;
import ru.silvestr.exception.NoUserByIdException;
import ru.silvestr.model.User;
import ru.silvestr.service.RoleService;
import ru.silvestr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserIncorrectId> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new UserIncorrectId(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoUserByIdException u) {
            throw new NoUserByIdException("User with username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserIncorrectId> pageDelete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(new UserIncorrectId("User deleted"), HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
                                           @Valid @RequestBody User user) {
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}