package ru.kata.demo.Exseption;

public class NoUserByIdException extends RuntimeException{

    public NoUserByIdException(String message) {
        super(message);
    }
}
