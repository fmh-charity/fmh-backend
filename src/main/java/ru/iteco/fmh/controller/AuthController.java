package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;
import ru.iteco.fmh.security.RefreshTokenRequest;
import ru.iteco.fmh.service.AuthService;

@Api(description = "Авторизация м Аутентификация пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider tokenProvider;

    @ApiOperation(value = "login")
    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @ApiOperation(value = "обновление токенов")
    @PostMapping("/refresh")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        if (!tokenProvider.validateJwtToken(refreshToken.getRefreshToken())) {
            throw new InvalidTokenException();
        }
        return authService.refreshToken(refreshToken);
    }

    @ApiOperation(value = "получение пользователя, который залогинен")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @GetMapping("/userInfo")
    public UserShortInfoDto getAuthorizedUser(Authentication authentication) {
        return authService.getAuthorizedUser(authentication);
    }
}
