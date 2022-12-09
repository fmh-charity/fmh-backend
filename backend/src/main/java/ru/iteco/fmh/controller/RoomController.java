package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.service.room.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Палаты")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех палат")
    @GetMapping
    public List<RoomDtoRs> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Просмотр карточки палаты")
    @GetMapping("/{id}")
    public RoomDtoRs getRoom(@Parameter(name = "Идентификатор палаты", required = true) @PathVariable("id") int id) {
        return roomService.getRoom(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Создание палаты")
    @PostMapping
    public RoomDtoRs createRoom(@RequestBody RoomDtoRq roomDto) {
        return roomService.createRoom(roomDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Редактирование палаты")
    @PutMapping("/{id}")
    public RoomDtoRs updateRoom(@PathVariable("id") int id, @RequestBody RoomDtoRq roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление палату")
    @DeleteMapping("/{id}")
    public void deleteRoom(@Parameter(name = "Идентификатор палаты", required = true) @PathVariable("id") int id) {
        roomService.deleteRoom(id);
    }
}