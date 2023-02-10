package ru.iteco.fmh.exceptions;

import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;

public class UserExistsException extends AuthenticationException {
    public UserExistsException(@Nullable String message) {
        super(message);
    }
}
