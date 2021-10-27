package ru.iteco.fmh.exceptions;

import org.springframework.security.core.AuthenticationException;

//401
public class ErrorUnauthorized extends AuthenticationException {
    public ErrorUnauthorized(String msg) {
        super(msg);
    }
}
