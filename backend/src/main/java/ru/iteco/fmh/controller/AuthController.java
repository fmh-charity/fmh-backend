package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;
import ru.iteco.fmh.security.RefreshTokenRequest;
import ru.iteco.fmh.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Авторизация и аутентификация пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider tokenProvider;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @Operation(summary = "Обновление токенов")
    @PostMapping("/refresh")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        if (!tokenProvider.validateJwtToken(refreshToken.getRefreshToken())) {
            throw new InvalidTokenException();
        }
        return authService.refreshToken(refreshToken);
    }

    @Operation(summary = "Получение текущего авторизованного пользователя")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @GetMapping("/userInfo")
    public UserShortInfoDto getAuthorizedUser(Authentication authentication) {
        return authService.getAuthorizedUser(authentication);
    }
}