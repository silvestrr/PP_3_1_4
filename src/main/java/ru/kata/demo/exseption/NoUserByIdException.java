package ru.kata.demo.exseption;

public class NoUserByIdException extends RuntimeException {

    public NoUserByIdException(String message) {
        super(message);
    }
}
