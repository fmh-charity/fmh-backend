package ru.iteco.fmh.exceptions;

import org.springframework.http.HttpStatus;

/**
 * возвращаемые коды ошибок на фронт
 */
public enum ErrorCodes {

    ERR_INVALID_LOGIN(HttpStatus.UNAUTHORIZED),
    ERR_UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_INVALID_REFRESH(HttpStatus.UNAUTHORIZED);

    private HttpStatus httpStatus;

    ErrorCodes(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
