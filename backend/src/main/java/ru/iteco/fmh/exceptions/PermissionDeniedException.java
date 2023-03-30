package ru.iteco.fmh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, value = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(@Nullable String message) {
        super(message);
    }
}
