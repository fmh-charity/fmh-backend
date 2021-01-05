package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.*;

import java.util.List;

/**
 * Информация по пользователю
 */
@Api(description = "Информация по пользователю")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "реестр всех пользователей с учетом пагинации")
    @GetMapping
    public List<UserShortInfoDto> getAllUsers(
            @ApiParam(value = "начальная позиция пагинации", required = true)@RequestParam("offset") Integer offset,
            @ApiParam(value = "конечная позиция пагинации", required = true)@RequestParam("limit") Integer limit,
            @ApiParam(value = "показывать только активных")@RequestParam("show_active") Boolean showActive
    ){
        return null;
    }

    @ApiOperation(value = "возвращает информацию по пользователю")
    @GetMapping("/{id}")
    public UserDto getUser(
            @ApiParam(value = "идентификатор пользователя", required = true)@PathVariable Integer id
    ){
        return null;
    }

    @ApiOperation(value = "создание пользователя")
    @PostMapping
    public Integer createUser(
            @RequestBody UserDto userDto
    ){
        return null;
    }

    @ApiOperation(value = "изменение пользователя")
    @PatchMapping
    public void updateUser(
            @RequestBody UserDto userDto
    ){

    }



}
