package ru.silvestr.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class NoUserByIdException extends DataIntegrityViolationException {
    public NoUserByIdException(String msg) {
        super(msg);
    }
}
