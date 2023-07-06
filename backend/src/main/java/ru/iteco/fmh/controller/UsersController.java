package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.user.ProfileChangingRequest;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.service.user.UserService;
import ru.iteco.fmh.service.verification.token.VerificationTokenService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
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
    public List<UserShortInfoDto> getAllUsers(
            @Parameter(name = "pages", description = "От 0")
            @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @Parameter(name = "elements", description = "От 1 до 200")
            @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @Parameter(name = "showConfirmed", description = "Сортировка по статусу пользователя")
            @RequestParam(required = false) Boolean showConfirmed) {
        return userService.getAllUsers(PageRequest.of(pages, elements), showConfirmed);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "получение информации о пользователе по id")
    @GetMapping("/{userId}")
    public UserInfoDto getUserInfo(@Parameter (description = "Идентификатор пользователя", required = true)
                                       @PathVariable("userId") int userId) {
        return userService.getUserInfo(userId);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Редактирование карточки Пользователя")
    @PutMapping("/{userId}")
    public UserShortInfoDto changeUser(@Parameter(description = "Идентификатор пользователя", required = true)
                                       @PathVariable("userId") int userId,
                                       @Parameter(description = "Информация для обновления пользователя", required = true)
                                       @RequestBody @Valid ProfileChangingRequest profileChangingRequest) {
        return userService.updateUser(userId, profileChangingRequest);
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

    @Operation(summary = "Подтверждение роли пользователя")
    @Secured("ROLE_ADMINISTRATOR")
    @PostMapping("/{userId}/confirmRole")
    public UserShortInfoDto confirmUserRole(@Parameter(description = "Идентификатор пользователя",
            required = true) @PathVariable int userId) {
        return userService.confirmUserRole(userId);
    }
}
