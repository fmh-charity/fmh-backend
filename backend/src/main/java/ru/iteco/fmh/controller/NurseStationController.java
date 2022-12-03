package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.post.NurseStationDto;
import ru.iteco.fmh.dto.post.NurseStationDtoRq;
import ru.iteco.fmh.dto.post.NurseStationDtoRs;
import ru.iteco.fmh.service.post.NurseStationService;

import java.util.List;

@RestController
@Api("Посты")
@RequiredArgsConstructor
@RequestMapping("/nurse_stations")
public class NurseStationController {

    private final NurseStationService nurseStationService;

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Список всех постов")
    @GetMapping
    public List<NurseStationDto> getAllNurseStations() {
        return nurseStationService.getAll();
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Создание нового поста")
    @PostMapping
    public NurseStationDtoRs createNurseStation(
            @RequestBody NurseStationDtoRq nurseStationDto) {
        return nurseStationService.createNurseStation(nurseStationDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Редактировать пост")
    @PutMapping("/{id}")
    public NurseStationDtoRs updateNurseStation(
            @PathVariable(value = "id") int id,
            @RequestBody NurseStationDtoRq nurseStationDto) {
        return nurseStationService.updateNurseStation(id, nurseStationDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Просмотр поста")
    @GetMapping("/{id}")
    public NurseStationDto getNurseStation(
            @ApiParam(value = "Идентификатор поста", required = true)
            @PathVariable("id") int id) {
        return nurseStationService.getNurseStation(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Удалить пост")
    @DeleteMapping("/{id}")
    public void deletePost(
            @ApiParam(value = "Идентификатор поста", required = true)
            @PathVariable("id") int id) {
        nurseStationService.deleteNurseStation(id);
    }

}
