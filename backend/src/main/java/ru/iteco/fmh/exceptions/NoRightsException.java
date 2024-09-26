package ru.iteco.fmh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, value = HttpStatus.FORBIDDEN)
public class NoRightsException extends RuntimeException {
    public NoRightsException() {
        super("Недостаточно прав");
    }

    public NoRightsException(String message) {
        super(message);
    }
}
