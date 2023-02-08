package ru.iteco.fmh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, value = HttpStatus.UNAUTHORIZED)
public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("ERR_INVALID_LOGIN");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
