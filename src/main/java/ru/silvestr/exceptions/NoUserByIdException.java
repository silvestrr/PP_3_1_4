package ru.silvestr.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class NoUserByIdException extends DataIntegrityViolationException {
    public NoUserByIdException(String msg) {
        super(msg);
    }
}
