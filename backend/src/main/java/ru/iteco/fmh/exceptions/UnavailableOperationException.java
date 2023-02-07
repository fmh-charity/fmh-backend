package ru.iteco.fmh.exceptions;

import org.springframework.lang.Nullable;

public class UnavailableOperationException extends RuntimeException {
    public UnavailableOperationException(@Nullable String message) {
        super(message);
    }
}
