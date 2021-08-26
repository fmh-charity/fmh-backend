package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;

import java.util.List;

/**
 * Информация по пользователю
 */
@Api(description = "Информация по пользователю")
@RestController
@RequestMapping("/users")
public class UsersController {

    @ApiOperation(value = "реестр всех пользователей с учетом пагинации")
    @GetMapping
    public List<UserShortInfoDto> getAllUsers(
            @ApiParam(value = "начальная позиция пагинации", required = true) @RequestParam("offset") int offset,
            @ApiParam(value = "конечная позиция пагинации", required = true) @RequestParam("limit") int limit,
            @ApiParam(value = "показывать только активных") @RequestParam("show_active") Boolean showActive
    ) {
        return null;
    }

    @ApiOperation(value = "возвращает информацию по пользователю")
    @GetMapping("/{id}")
    public UserDto getUser(
            @ApiParam(value = "идентификатор пользователя", required = true) @PathVariable int id
    ) {
        return null;
    }

    @ApiOperation(value = "создание пользователя")
    @PostMapping
    public Integer createUser(
            @RequestBody UserDto userDto
    ) {
        return null;
    }

    @ApiOperation(value = "изменение пользователя")
    @PatchMapping
    public void updateUser(
            @RequestBody UserDto userDto
    ) {

    }
}
