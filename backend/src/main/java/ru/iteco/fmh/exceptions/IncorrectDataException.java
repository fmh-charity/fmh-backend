package ru.iteco.fmh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, value = HttpStatus.BAD_REQUEST)
public class IncorrectDataException extends IllegalArgumentException {
    public IncorrectDataException(@Nullable String message) {
        super(message);
    }
}
