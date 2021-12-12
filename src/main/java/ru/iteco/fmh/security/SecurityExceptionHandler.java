package ru.iteco.fmh.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityExceptionHandler implements AccessDeniedHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);
        response.sendError(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage());
    }
}
