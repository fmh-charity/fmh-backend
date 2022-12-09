package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.post.NurseStationDto;
import ru.iteco.fmh.dto.post.NurseStationDtoRq;
import ru.iteco.fmh.dto.post.NurseStationDtoRs;
import ru.iteco.fmh.service.post.NurseStationService;

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

@RestController
@Tag(name = "Посты")
@RequiredArgsConstructor
@RequestMapping("/nurse_stations")
public class NurseStationController {

    private final NurseStationService nurseStationService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Список всех постов")
    @GetMapping
    public List<NurseStationDto> getAllNurseStations() {
        return nurseStationService.getAll();
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание нового поста")
    @PostMapping
    public NurseStationDtoRs createNurseStation(@RequestBody NurseStationDtoRq nurseStationDto) {
        return nurseStationService.createNurseStation(nurseStationDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Редактирование поста")
    @PutMapping("/{id}")
    public NurseStationDtoRs updateNurseStation(@PathVariable(value = "id") int id, @RequestBody NurseStationDtoRq nurseStationDto) {
        return nurseStationService.updateNurseStation(id, nurseStationDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Просмотр поста")
    @GetMapping("/{id}")
    public NurseStationDto getNurseStation(@Parameter(name = "Идентификатор поста", required = true) @PathVariable("id") int id) {
        return nurseStationService.getNurseStation(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление поста")
    @DeleteMapping("/{id}")
    public void deletePost(@Parameter(name = "Идентификатор поста", required = true) @PathVariable("id") int id) {
        nurseStationService.deleteNurseStation(id);
    }
}