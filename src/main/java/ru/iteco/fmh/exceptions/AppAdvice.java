package ru.iteco.fmh.exceptions;

import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;

import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_LOGIN;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_REFRESH;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_UNEXPECTED;

@ControllerAdvice
public class AppAdvice {

    private static final Map<Class<? extends RuntimeException>, ErrorCodes> errors = Map.of(
            InvalidLoginException.class, ERR_INVALID_LOGIN,
            InvalidTokenException.class, ERR_INVALID_REFRESH
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCodes errorCode = Optional.ofNullable(errors.get(e.getClass())).orElse(ERR_UNEXPECTED);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder().code(errorCode).message(e.getMessage()).build());
    }

    @Builder
    private static class ErrorResponse {
        private ErrorCodes code;
        private String message;
    }
}
