package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;
import ru.iteco.fmh.service.AuthService;

@Api(description = "Авторизация м Аутентификация пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "login")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @ApiOperation(value = "обновление токенов")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @PostMapping("/update")
    public JwtResponse tokensUpdate(String refreshToken) {
        return authService.tokensUpdate(refreshToken);
    }

    @ApiOperation(value = "получение пользователя, который залогинен")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @PostMapping("/check")
    public UserShortInfoDto getAuthorizedUser(@RequestBody String accessToken) {
        return authService.getAuthorizedUser(accessToken);
    }
}
