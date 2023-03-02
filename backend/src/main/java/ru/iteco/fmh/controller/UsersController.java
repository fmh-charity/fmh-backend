package ru.iteco.fmh.controller;

import org.springframework.web.bind.annotation.RequestParam;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.service.user.UserService;
import ru.iteco.fmh.service.verification.token.VerificationTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Информация по пользователю
 */

@RequiredArgsConstructor
@Tag(name = "Информация по пользователю")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Реестр всех пользователей ")
    @GetMapping
    public List<UserShortInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Подтверждение Email пользователя")
    @GetMapping("verify/email")
    public void verifyEmail(@RequestParam("token") String token) {
        verificationTokenService.verifyEmail(token);
    }

    @Operation(summary = "Генерация токена и отправка письма для подтверждения")
    @PostMapping("/verification-email")
    public void generateAndSendVerificationEmail() {
        verificationTokenService.generateAndSendVerificationEmail();
    }
}
