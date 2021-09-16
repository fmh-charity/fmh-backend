package ru.iteco.fmh.exceptions;

//401
public class ErrorUnauthorized extends RuntimeException {
    public ErrorUnauthorized(String msg) {
        super(msg);
    }
}
