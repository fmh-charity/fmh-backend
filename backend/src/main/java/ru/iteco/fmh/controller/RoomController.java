package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.service.room.RoomService;

import java.util.List;

@Api(description = "Палаты")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех палат")
    @GetMapping
    public List<RoomDtoRs> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "просмотреть карточку палаты")
    @GetMapping("/{id}")
    public RoomDtoRs getRoom(
            @ApiParam(value = "идентификатор палаты", required = true) @PathVariable("id") int id) {
        return roomService.getRoom(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "создать палату")
    @PostMapping
    public RoomDtoRs createRoom(@RequestBody RoomDtoRq roomDto) {
        return roomService.createRoom(roomDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "редактировать палату")
    @PutMapping("/{id}")
    public RoomDtoRs updateRoom(@PathVariable("id") int id, @RequestBody RoomDtoRq roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "удалить палату")
    @DeleteMapping("/{id}")
    public void deleteRoom(@ApiParam(value = "идентификатор палаты", required = true) @PathVariable("id") int id) {
        roomService.deleteRoom(id);
    }

}
