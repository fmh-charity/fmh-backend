package ru.iteco.fmh.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ru.iteco.fmh.service.AuthService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_DUPLICATE_DATA;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INCORRECT_DATA;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_LOGIN;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_INVALID_REFRESH;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_MAX_UPLOAD;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_NOT_FOUND;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_NO_RIGHTS;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_SEND_MAIL;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_UNEXPECTED;
import static ru.iteco.fmh.exceptions.ErrorCodes.ERR_USER_EXISTS;

@ControllerAdvice
public class AppAdvice {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final Map<Class<? extends RuntimeException>, ErrorCodes> errors = Map.of(
            InvalidLoginException.class, ERR_INVALID_LOGIN,
            InvalidTokenException.class, ERR_INVALID_REFRESH,
            NoRightsException.class, ERR_NO_RIGHTS,
            NotFoundException.class, ERR_NOT_FOUND,
            UserExistsException.class, ERR_USER_EXISTS,
            MailException.class, ERR_SEND_MAIL,
            DuplicateDataException.class, ERR_DUPLICATE_DATA,
            IncorrectDataException.class, ERR_INCORRECT_DATA
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorCodes errorCode = Optional.ofNullable(errors.get(e.getClass())).orElse(ERR_UNEXPECTED);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder().code(errorCode).message(e.getMessage()).build());
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(Exception ex, WebRequest request) {
        MaxUploadSizeExceededException musee = (MaxUploadSizeExceededException) ex;
        SizeLimitExceededException slee = musee.getCause() instanceof SizeLimitExceededException
                ? (SizeLimitExceededException) musee.getCause() : null;
        long actualSize = slee == null ? Long.parseLong(Objects.requireNonNull(request.getHeader("Content-Length"))) : slee.getActualSize();
        int currentFileMb = (int) (actualSize / 1000 / 1000);
        String message = String.format("Превышен максимальный размер файла %s, текущий размер %dMB", maxFileSize, currentFileMb);
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ErrorResponse.builder().code(ERR_MAX_UPLOAD).message(message).build());
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
    public ValidationErrorResponse onAsyncException(MethodArgumentNotValidException e) {
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
