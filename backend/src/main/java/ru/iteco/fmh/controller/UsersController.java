package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.service.user.UserService;

import java.util.List;

/**
 * Информация по пользователю
 */

@RequiredArgsConstructor
@Api(description = "Информация по пользователю")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "реестр всех пользователей ")
    @GetMapping
    public List<UserShortInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
