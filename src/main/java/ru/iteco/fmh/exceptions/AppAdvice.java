package ru.iteco.fmh.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.iteco.fmh.service.AuthService;

import java.util.Map;
import java.util.Optional;

import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_LOGIN;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_REFRESH;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_UNEXPECTED;

@ControllerAdvice
public class AppAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final Map<Class<? extends RuntimeException>, ErrorCodes> errors = Map.of(
            InvalidLoginException.class, ERR_INVALID_LOGIN,
            InvalidTokenException.class, ERR_INVALID_REFRESH
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorCodes errorCode = Optional.ofNullable(errors.get(e.getClass())).orElse(ERR_UNEXPECTED);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder().code(errorCode).message(e.getMessage()).build());
    }

    @Builder
    @Getter
    @Setter
    private static class ErrorResponse {
        private ErrorCodes code;
        private String message;
    }
}
