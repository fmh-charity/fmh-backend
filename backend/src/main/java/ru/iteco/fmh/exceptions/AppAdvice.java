package ru.iteco.fmh.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.iteco.fmh.service.AuthService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.iteco.fmh.exceptions.ErrorCodes.*;

@ControllerAdvice
public class AppAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final Map<Class<? extends RuntimeException>, ErrorCodes> errors = Map.of(
            InvalidLoginException.class, ERR_INVALID_LOGIN,
            InvalidTokenException.class, ERR_INVALID_REFRESH,
            NoRightsException.class, ERR_NO_RIGHTS,
            NotFoundException.class, ERR_NOT_FOUND,
            DuplicateDataException.class, ERR_DUPLICATE_DATA
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorCodes errorCode = Optional.ofNullable(errors.get(e.getClass())).orElse(ERR_UNEXPECTED);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder().code(errorCode).message(e.getMessage()).build());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @Getter
    @Setter
    @Builder
    private static class ErrorResponse {
        private ErrorCodes code;
        private String message;
    }

    public record ValidationErrorResponse(List<Violation> violations) {
    }

    public record Violation(String fieldName, String message) {
    }
}
